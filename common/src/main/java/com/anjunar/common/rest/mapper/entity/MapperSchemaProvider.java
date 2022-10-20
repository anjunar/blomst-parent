package com.anjunar.common.rest.mapper.entity;

import com.anjunar.common.ddd.AbstractRight;
import com.anjunar.common.rest.api.AbstractSchemaEntity;
import com.anjunar.common.rest.mapper.annotations.MapperVisibility;
import com.anjunar.common.security.*;
import com.anjunar.introspector.bean.BeanIntrospector;
import com.anjunar.introspector.bean.BeanModel;
import com.anjunar.introspector.bean.BeanProperty;
import jakarta.inject.Inject;
import jakarta.persistence.Column;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Table;

import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public class MapperSchemaProvider implements SecurityProvider {

    private final IdentityStore identityStore;

    private final EntityManager entityManager;

    @Inject
    public MapperSchemaProvider(IdentityStore identityStore, EntityManager entityManager) {
        this.identityStore = identityStore;
        this.entityManager = entityManager;
    }

    public MapperSchemaProvider() {
        this(null, null);
    }

    @Override
    public <S, D> boolean execute(S source, BeanProperty<S, ?> sourceProperty, D destination, BeanProperty<D, Object> destinationProperty) {
        boolean isAllowed = true;
        if (visibility(destinationProperty) && source instanceof OwnerProvider) {
            isAllowed = isAllowedWithSchema((OwnerProvider) source, sourceProperty);
        }
        return isAllowed;
    }

    public <S> boolean visibility(BeanProperty<S, ?> property) {
        return property.getAnnotation(MapperVisibility.class) != null;
    }

    public boolean isAllowedWithSchema(OwnerProvider entity, BeanProperty<?, ?> property) {
        if (entity.getOwner().equals(identityStore.getUser())) {
            return true;
        }

        Role role = entityManager.createQuery("select r from Role r where r.name = :name", Role.class)
                .setParameter("name", "Administrator")
                .getSingleResult();

        if (identityStore.getUser().getRoles().contains(role)) {
            return true;
        }

        try {
            BeanModel<? extends OwnerProvider> model = BeanIntrospector.create(entity.getClass());
            String tableName = null;
            Table annotation = model.getAnnotation(Table.class);
            if (Objects.nonNull(annotation)) {
                tableName = annotation.name();
            } else {
                tableName = entity.getClass().getSimpleName();
            }

            String columnName = null;
            Column column = property.getAnnotation(Column.class);
            if (Objects.nonNull(column)) {
                columnName = column.name();
            } else {
                columnName = property.getKey();
            }

            AbstractRight<?> right = entityManager.createQuery("select s from " + tableName + "Right s where s.source = :source and s.column = :column", AbstractRight.class)
                    .setParameter("source", entity)
                    .setParameter("column", columnName)
                    .getSingleResult();

            Category category = entityManager.createQuery("select c from Category c where function('jsonPathAsText', c.i18nName, :locale) = :name", Category.class)
                    .setParameter("locale", Locale.forLanguageTag("en-DE"))
                    .setParameter("name", "Everybody")
                    .getSingleResult();

            if (right.getCategories().contains(category)) {
                return true;
            }

            UserConnection userConnection = entityManager.createQuery("select c from UserConnection c where c.to = :to and c.from = :from", UserConnection.class)
                    .setParameter("to", identityStore.getUser())
                    .setParameter("from", entity.getOwner())
                    .getSingleResult();

            Set<Category> visibility = right.getCategories();

            return visibility.contains(userConnection.getCategory());
        } catch (NoResultException e) {
            return false;
        }
    }

}
