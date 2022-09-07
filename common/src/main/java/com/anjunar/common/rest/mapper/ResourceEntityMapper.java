package com.anjunar.common.rest.mapper;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.mapper.annotations.MapperConverter;
import com.anjunar.common.rest.mapper.annotations.MapperConverterType;
import com.anjunar.common.rest.mapper.annotations.MapperMapProjection;
import com.anjunar.common.rest.mapper.annotations.MapperProjection;
import com.anjunar.common.rest.mapper.entity.SecurityProvider;
import com.anjunar.common.rest.schema.CategoryType;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.security.Category;
import com.anjunar.common.security.EntitySchema;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.OwnerProvider;
import com.anjunar.introspector.bean.BeanIntrospector;
import com.anjunar.introspector.bean.BeanModel;
import com.anjunar.introspector.bean.BeanProperty;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@ApplicationScoped
@SuppressWarnings({"unchecked", "UnstableApiUsage", "rawtypes"})
public class ResourceEntityMapper {

    private static final Logger log = LoggerFactory.getLogger(ResourceEntityMapper.class);

    private final IdentityProvider identityProvider;

    private final EntityManager entityManager;

    private final Instance<SecurityProvider> securityProviders;

    @Inject
    public ResourceEntityMapper(IdentityProvider identityProvider, EntityManager entityManager, Instance<SecurityProvider> securityProviders) {
        this.identityProvider = identityProvider;
        this.entityManager = entityManager;
        this.securityProviders = securityProviders;
    }

    public ResourceEntityMapper() {
        this(null, null, null);
    }

    static <D> D getNewInstance(Class<D> destinationClass) {
        try {
            return destinationClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public IdentityProvider identityProvider() {
        return identityProvider;
    }

    public EntityManager entityManager() {
        return entityManager;
    }

    public <S extends AbstractEntity, D extends AbstractRestEntity, V> D map(S source, Class<D> destinationClass) {
        return map(source, destinationClass, null);
    }

    public <S extends AbstractEntity, D extends AbstractRestEntity, V> D map(S source, Class<D> destinationClass, Class<V> projection) {
        D destination = getNewInstance(destinationClass);

        if (source instanceof OwnerProvider ownerProvider) {
            if (identityProvider.getUser().equals(ownerProvider.getOwner())) {
                loadSchema((OwnerProvider) source, destination);
            } else {
                for (Map.Entry<String, JsonNode> entry : destination.getSchema().getProperties().entrySet()) {
                    entry.getValue().setVisibility(false);
                }
            }
        }

        BeanModel<S> sourceModel = (BeanModel<S>) BeanIntrospector.create(source.getClass());
        BeanModel<D> destinationModel = BeanIntrospector.create(destinationClass);
        BeanModel<V> projectionModel;
        if (projection != null && projection != void.class) {
            projectionModel = BeanIntrospector.create(projection);
        } else {
            projectionModel = (BeanModel<V>) destinationModel;
        }

        for (BeanProperty<S, ?> sourceProperty : sourceModel.getProperties()) {
            BeanProperty<D, Object> destinationProperty = (BeanProperty<D, Object>) destinationModel.get(sourceProperty.getKey());
            BeanProperty<V, ?> projectionProperty = projectionModel.get(sourceProperty.getKey());

            if (Objects.nonNull(destinationProperty)) {
                Object sourcePropertyInstance = sourceProperty.apply(source);
                Object destinationPropertyInstance = destinationProperty.apply(destination);

                if (Objects.nonNull(sourcePropertyInstance)) {
                    if (securityProviders.stream().allMatch(securityProvider -> securityProvider.execute(source, sourceProperty, destination, destinationProperty)) && Objects.nonNull(projectionProperty)) {
                        switch (sourcePropertyInstance) {
                            case Collection<?> collection -> processCollection(
                                    (Collection<Object>) sourcePropertyInstance,
                                    sourceProperty,
                                    (Collection<Object>) destinationPropertyInstance,
                                    destinationProperty
                            );
                            case Map<?, ?> map -> processMap(
                                    (Map<Object, Object>) sourcePropertyInstance,
                                    sourceProperty,
                                    (Map<Object, Object>) destinationPropertyInstance,
                                    destinationProperty
                            );
                            default -> processBean(
                                    sourcePropertyInstance,
                                    sourceProperty,
                                    destinationPropertyInstance,
                                    destinationProperty,
                                    destination
                            );
                        }
                    } else {
                        destination.getSchema().remove(destinationProperty.getKey());
                    }
                }
            }
        }
        return destination;
    }

    private <S extends AbstractEntity, D extends AbstractRestEntity> void processBean(Object sourcePropertyInstance,
                                                                                      BeanProperty<S, ?> sourceProperty,
                                                                                      Object destinationPropertyInstance,
                                                                                      BeanProperty<D, Object> destinationProperty,
                                                                                      D destination) {
        Class<?> sourcePropertyType = sourceProperty.getType().getRawType();
        Class<?> destinationPropertyType = destinationProperty.getType().getRawType();

        MapperConverter mapperConverter = destinationProperty.getAnnotation(MapperConverter.class);
        if (mapperConverter == null) {
            if (AbstractEntity.class.isAssignableFrom(sourcePropertyType)) {
                AbstractEntity entity = (AbstractEntity) sourcePropertyInstance;
                if (UUID.class.isAssignableFrom(destinationPropertyType)) {
                    destinationProperty.accept(destination, entity.getId());
                } else {
                    MapperProjection mapperProjection = destinationProperty.getAnnotation(MapperProjection.class);
                    AbstractRestEntity restEntity;
                    if (mapperProjection == null) {
                        restEntity = map(entity, (Class<? extends AbstractRestEntity>) destinationPropertyType, null);
                    } else {
                        restEntity = map(entity, (Class<? extends AbstractRestEntity>) destinationPropertyType, mapperProjection.value());
                    }
                    destinationProperty.accept(destination, restEntity);
                }
            } else {
                destinationProperty.accept(destination, sourcePropertyInstance);
            }
        } else {
            MapperConverterType converter = getNewInstance(mapperConverter.value());
            Object restEntity = converter.factory(sourcePropertyInstance);
            destinationProperty.accept(destination, restEntity);
        }

    }

    private <S extends AbstractEntity, D extends AbstractRestEntity> void processMap(Map<Object, Object> sourcePropertyInstance,
                                                                                     BeanProperty<S, ?> sourceProperty,
                                                                                     Map<Object, Object> destinationPropertyInstance,
                                                                                     BeanProperty<D, Object> destinationProperty) {
        Class<?> sourceMapKeyType = sourceProperty
                .getType()
                .resolveType(Map.class.getTypeParameters()[0])
                .getRawType();

        Class<?> sourceMapValueType = sourceProperty
                .getType()
                .resolveType(Map.class.getTypeParameters()[1])
                .getRawType();

        Class<?> destinationMapKeyType = destinationProperty
                .getType()
                .resolveType(Map.class.getTypeParameters()[0])
                .getRawType();

        Class<?> destinationMapValueType = destinationProperty
                .getType()
                .resolveType(Map.class.getTypeParameters()[1])
                .getRawType();

        MapperMapProjection mapProjection = destinationProperty.getAnnotation(MapperMapProjection.class);
        Class<?> keyProjection = void.class;
        Class<?> valueProjection = void.class;
        if (mapProjection != null) {
            keyProjection = mapProjection.key();
            valueProjection = mapProjection.value();
        }

        for (Map.Entry<Object, Object> entry : sourcePropertyInstance.entrySet()) {
            if (AbstractEntity.class.isAssignableFrom(sourceMapKeyType)) {
                AbstractEntity entity = (AbstractEntity) entry.getKey();
                AbstractRestEntity restEntity = map(entity, (Class<? extends AbstractRestEntity>) destinationMapKeyType, keyProjection);
                if (AbstractEntity.class.isAssignableFrom(sourceMapValueType)) {
                    AbstractEntity entity2 = (AbstractEntity) entry.getValue();
                    AbstractRestEntity restEntity2 = map(entity2, (Class<? extends AbstractRestEntity>) destinationMapValueType, valueProjection);
                    destinationPropertyInstance.put(restEntity, restEntity2);
                } else {
                    destinationPropertyInstance.put(restEntity, entry.getValue());
                }
            } else {
                destinationPropertyInstance.put(entry.getKey(), entry.getValue());
            }
        }

    }

    private <S extends AbstractEntity, D extends AbstractRestEntity> void processCollection(Collection<Object> sourcePropertyInstance,
                                                                                            BeanProperty<S, ?> sourceProperty,
                                                                                            Collection<Object> destinationPropertyInstance,
                                                                                            BeanProperty<D, Object> destinationProperty) {
        Class<?> sourceCollectionType = sourceProperty
                .getType()
                .resolveType(Collection.class.getTypeParameters()[0])
                .getRawType();

        Class<?> destinationCollectionType = destinationProperty
                .getType()
                .resolveType(Collection.class.getTypeParameters()[0])
                .getRawType();

        for (Object element : sourcePropertyInstance) {
            if (AbstractEntity.class.isAssignableFrom(sourceCollectionType)) {
                AbstractEntity entity = (AbstractEntity) element;
                MapperProjection mapperProjection = destinationProperty.getAnnotation(MapperProjection.class);
                AbstractRestEntity restEntity;
                if (mapperProjection == null) {
                    restEntity = map(entity, (Class<? extends AbstractRestEntity>) destinationCollectionType, null);
                } else {
                    restEntity = map(entity, (Class<? extends AbstractRestEntity>) destinationCollectionType, mapperProjection.value());
                }
                destinationPropertyInstance.add(restEntity);
            } else {
                destinationPropertyInstance.add(element);
            }
        }
    }


    private <S extends AbstractEntity, D extends AbstractRestEntity> void loadSchema(OwnerProvider source, D destination) {
        JsonObject schema = destination.getSchema();
        for (Map.Entry<String, JsonNode> entry : schema.getProperties().entrySet()) {
            try {
                if (entry.getValue().getVisibility() != null && entry.getValue().getVisibility()) {
                    Set<CategoryType> categories = entry.getValue().getCategories();
                    EntityManager entityManager = entityManager();
                    EntitySchema entitySchema = entityManager.createQuery("select s from EntitySchema s where s.owner = :owner and s.entity = :entity and s.property = :property", EntitySchema.class)
                            .setParameter("owner", source.getOwner())
                            .setParameter("entity", destination.getClass())
                            .setParameter("property", entry.getKey())
                            .getSingleResult();

                    for (Category category : entitySchema.getVisibility()) {
                        CategoryType categoryType = map(category, CategoryType.class, null);
                        categories.add(categoryType);
                    }
                }
            } catch (NoResultException e) {
                EntitySchema schemaItem = new EntitySchema();
                schemaItem.setEntity(destination.getClass());
                schemaItem.setOwner(identityProvider.getUser());
                schemaItem.setProperty(entry.getKey());
                entityManager.persist(schemaItem);
            }
        }
    }

}
