package com.anjunar.common.rest.mapper.entity;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.mapper.annotations.MapperVisibility;
import com.anjunar.common.security.*;
import com.anjunar.introspector.bean.BeanProperty;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.Locale;
import java.util.Set;

public class MapperSchemaProvider implements SecurityProvider {

    private final IdentityProvider identityProvider;

    private final EntityManager entityManager;

    @Inject
    public MapperSchemaProvider(IdentityProvider identityProvider, EntityManager entityManager) {
        this.identityProvider = identityProvider;
        this.entityManager = entityManager;
    }

    public MapperSchemaProvider() {
        this(null, null);
    }

    @Override
    public <S extends AbstractEntity, D extends AbstractRestEntity> boolean execute(S source, BeanProperty<S, ?> sourceProperty, D destination, BeanProperty<D, Object> destinationProperty) {
        boolean isAllowed = true;
        if (visibility(destination, destinationProperty) && source instanceof OwnerProvider) {
            isAllowed = isAllowedWithSchema((OwnerProvider) source, sourceProperty.getKey(), destination.getClass());
        }
        return isAllowed;
    }

    public <S extends AbstractRestEntity, D extends AbstractRestEntity> boolean visibility(D destination, BeanProperty<S, ?> property) {
        return property.getAnnotation(MapperVisibility.class) != null;
    }

    public boolean isAllowedWithSchema(OwnerProvider entity, String property, Class<?> aClass) {
        if (entity.getOwner().equals(identityProvider.getUser())) {
            return true;
        }

        try {
            EntitySchema schemaItem = entityManager.createQuery("select s from EntitySchema s where s.property = :property and s.entity = :entity and s.owner = :owner", EntitySchema.class)
                    .setParameter("property", property)
                    .setParameter("entity", aClass)
                    .setParameter("owner", entity.getOwner())
                    .getSingleResult();

            Category category = entityManager.createQuery("select c from Category c where function('jsonPathAsText', c.i18nName, :locale) = :name", Category.class)
                    .setParameter("locale", Locale.forLanguageTag("en-DE"))
                    .setParameter("name", "Everybody")
                    .getSingleResult();

            if (schemaItem.getVisibility().contains(category)) {
                return true;
            }

            UserConnection userConnection = entityManager.createQuery("select c from UserConnection c where c.to = :to and c.from = :from", UserConnection.class)
                    .setParameter("to", identityProvider.getUser())
                    .setParameter("from", entity.getOwner())
                    .getSingleResult();

            Set<Category> visibility = schemaItem.getVisibility();

            return visibility.contains(userConnection.getCategory());
        } catch (NoResultException e) {
            return false;
        }
    }

}