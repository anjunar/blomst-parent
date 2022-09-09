package com.anjunar.common.rest.mapper.rest;

import com.anjunar.common.rest.api.AbstractSchemaEntity;
import com.anjunar.common.rest.mapper.annotations.MapperVisibility;
import com.anjunar.common.security.*;
import com.anjunar.introspector.bean.BeanProperty;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.Set;

public class MapperSchemaProvider implements SecurityProvider{

    private final IdentityManager identityManager;

    private final EntityManager entityManager;

    @Inject
    public MapperSchemaProvider(IdentityManager identityManager, EntityManager entityManager) {
        this.identityManager = identityManager;
        this.entityManager = entityManager;
    }

    public MapperSchemaProvider() {
        this(null, null);
    }

    @Override
    public <S extends AbstractSchemaEntity, D> boolean execute(S source, BeanProperty<S, ?> sourceProperty, D destination, BeanProperty<D, Object> destinationProperty) {
        boolean isAllowed = true;
        if (visibility(source, sourceProperty) && source instanceof OwnerProvider) {
            isAllowed = isAllowedWithSchema((OwnerProvider) source, destinationProperty.getKey(), destination.getClass());
        }
        return isAllowed;
    }

    public <S extends AbstractSchemaEntity, D extends AbstractSchemaEntity> boolean visibility(D destination, BeanProperty<S, ?> property) {
        return property.getAnnotation(MapperVisibility.class) != null;
    }

    public boolean isAllowedWithSchema(OwnerProvider entity, String property, Class<?> aClass) {
        if (entity.getOwner().equals(identityManager.getUser())) {
            return true;
        }

        try {
            EntitySchema schemaItem = entityManager.createQuery("select s from EntitySchema s where s.property = :property and s.entity = :entity and s.owner = :owner", EntitySchema.class)
                    .setParameter("property", property)
                    .setParameter("entity", aClass)
                    .setParameter("owner", entity.getOwner())
                    .getSingleResult();

            UserConnection userConnection = entityManager.createQuery("select c from UserConnection c where c.to = :to and c.from = :from", UserConnection.class)
                    .setParameter("to", identityManager.getUser())
                    .setParameter("from", entity.getOwner())
                    .getSingleResult();

            Set<Category> visibility = schemaItem.getVisibility();

            return visibility.contains(userConnection.getCategory());
        } catch (NoResultException e) {
            return false;
        }
    }

}
