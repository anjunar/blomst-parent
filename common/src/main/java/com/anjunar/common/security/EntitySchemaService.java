package com.anjunar.common.security;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.ddd.OnPersist;
import com.anjunar.common.rest.schemamapper.MapperSchema;
import com.anjunar.introspector.bean.BeanIntrospector;
import com.anjunar.introspector.bean.BeanModel;
import com.anjunar.introspector.bean.BeanProperty;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EntitySchemaService {

    @Transactional
    private void onPersist(@Observes @OnPersist AbstractEntity entity, EntityManager entityManager, IdentityProvider identityProvider) {
        BeanModel<? extends AbstractEntity> beanModel = BeanIntrospector.create(entity.getClass());

        for (BeanProperty<? extends AbstractEntity, ?> property : beanModel.getProperties()) {
            MapperSchema annotation = property.getAnnotation(MapperSchema.class);
            if (annotation != null) {
                EntitySchema schemaItem = new EntitySchema();
                schemaItem.setEntity(entity.getClass());
                schemaItem.setOwner(identityProvider.getUser());
                schemaItem.setProperty(property.getKey());
                entityManager.persist(schemaItem);
            }
        }

    }

}
