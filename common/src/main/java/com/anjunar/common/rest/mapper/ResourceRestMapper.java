package com.anjunar.common.rest.mapper;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.AbstractSchemaEntity;
import com.anjunar.common.rest.mapper.annotations.MapperConverter;
import com.anjunar.common.rest.mapper.annotations.MapperConverterType;
import com.anjunar.common.rest.mapper.rest.SecurityProvider;
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
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@ApplicationScoped
@SuppressWarnings({"unchecked", "UnstableApiUsage", "rawtypes"})
public class ResourceRestMapper {

    private static final Logger log = LoggerFactory.getLogger(ResourceRestMapper.class);

    private final IdentityProvider identityProvider;

    private final EntityManager entityManager;

    private final Instance<SecurityProvider> securityProviders;

    @Inject
    public ResourceRestMapper(IdentityProvider identityProvider, EntityManager entityManager, Instance<SecurityProvider> securityProviders) {
        this.identityProvider = identityProvider;
        this.entityManager = entityManager;
        this.securityProviders = securityProviders;
    }

    public ResourceRestMapper() {
        this(null, null, null);
    }

    public <S extends AbstractRestEntity, D extends AbstractEntity> D map(S source, Class<D> destinationClass) {
        UUID id = source.getId();
        D destination;
        if (id == null) {
            destination = getNewInstance(destinationClass);
        } else {
            destination = entityManager.find(destinationClass, id);
        }

        if (destination instanceof OwnerProvider ownerProvider) {
            if (identityProvider.getUser().equals(ownerProvider.getOwner())) {
                saveSchema(source, destination);
            } else {
                throw new WebApplicationException(Response.Status.FORBIDDEN);
            }
        }

        BeanModel<S> sourceModel = (BeanModel<S>) BeanIntrospector.create(source.getClass());
        BeanModel<D> destinationModel = BeanIntrospector.create(destinationClass);

        for (BeanProperty<S, ?> sourceProperty : sourceModel.getProperties()) {
            BeanProperty<D, Object> destinationProperty = (BeanProperty<D, Object>) destinationModel.get(sourceProperty.getKey());

            if (Objects.nonNull(destinationProperty)) {
                Object sourcePropertyInstance = sourceProperty.apply(source);
                Object destinationPropertyInstance = destinationProperty.apply(destination);

                if (Objects.nonNull(sourcePropertyInstance) && isDirty(source, sourceProperty)) {
                    if (securityProviders.stream().anyMatch(securityProvider -> securityProvider.execute(source, sourceProperty, destination, destinationProperty))) {
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
                    }
                }
            }
        }

        return destination;
    }

    static <D> D getNewInstance(Class<D> destinationClass) {
        try {
            return destinationClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private <S extends AbstractRestEntity, D extends AbstractEntity> void processBean(Object sourcePropertyInstance,
                                                                                      BeanProperty<S, ?> sourceProperty,
                                                                                      Object destinationPropertyInstance,
                                                                                      BeanProperty<D, Object> destinationProperty, D destination) {
        Class<?> sourcePropertyType = sourceProperty.getType().getRawType();
        Class<?> destinationPropertyType = destinationProperty.getType().getRawType();

        MapperConverter mapperConverter = sourceProperty.getAnnotation(MapperConverter.class);
        if (mapperConverter == null) {
            if (AbstractRestEntity.class.isAssignableFrom(sourcePropertyType)) {
                AbstractRestEntity entity = (AbstractRestEntity) sourcePropertyInstance;
                AbstractEntity restEntity = map(entity, (Class<? extends AbstractEntity>) destinationPropertyType);
                destinationProperty.accept(destination, restEntity);
            } else {
                if (UUID.class.isAssignableFrom(sourcePropertyType)) {
                    UUID uuid = (UUID) sourcePropertyInstance;
                    Object entity = entityManager.find(destinationPropertyType, uuid);
                    destinationProperty.accept(destination, entity);
                } else {
                    destinationProperty.accept(destination, sourcePropertyInstance);
                }
            }
        } else {
            MapperConverterType converter = getNewInstance(mapperConverter.value());
            Object restEntity = converter.updater(sourcePropertyInstance);
            destinationProperty.accept(destination, restEntity);
        }
    }

    private <S extends AbstractRestEntity, D extends AbstractEntity> void processMap(Map<Object, Object> sourcePropertyInstance,
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

        for (Map.Entry<Object, Object> entry : sourcePropertyInstance.entrySet()) {
            if (AbstractRestEntity.class.isAssignableFrom(sourceMapKeyType)) {
                AbstractRestEntity restKeyEntity = (AbstractRestEntity) entry.getKey();
                AbstractEntity keyEntity = map(restKeyEntity, (Class<? extends AbstractEntity>) destinationMapKeyType);
                if (AbstractRestEntity.class.isAssignableFrom(sourceMapValueType)) {
                    AbstractRestEntity restValueEntity = (AbstractRestEntity) entry.getValue();
                    AbstractEntity valueEntity = map(restValueEntity, (Class<? extends AbstractEntity>) destinationMapValueType);
                    destinationPropertyInstance.put(keyEntity, valueEntity);
                } else {
                    destinationPropertyInstance.put(keyEntity, entry.getValue());
                }
            } else {
                destinationPropertyInstance.put(entry.getKey(), entry.getValue());
            }
        }
    }

    private <S extends AbstractRestEntity, D extends AbstractEntity> void processCollection(Collection<Object> sourcePropertyInstance,
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

        destinationPropertyInstance.clear();

        for (Object element : sourcePropertyInstance) {
            if (AbstractRestEntity.class.isAssignableFrom(sourceCollectionType)) {
                AbstractRestEntity restEntity = (AbstractRestEntity) element;
                AbstractEntity entity = map(restEntity, (Class<? extends AbstractEntity>) destinationCollectionType);
                destinationPropertyInstance.add(entity);
            } else {
                destinationPropertyInstance.add(element);
            }
        }
    }

    private <S extends AbstractRestEntity, D extends AbstractEntity> void saveSchema(S source, D destination) {
        JsonObject schema = source.getSchema();
        for (Map.Entry<String, JsonNode> entry : schema.getProperties().entrySet()) {
            if (entry.getValue().getVisibility() != null && entry.getValue().getVisibility()) {
                Set<CategoryType> categories = entry.getValue().getCategories();
                try {
                    EntitySchema entitySchema = entityManager.createQuery("select s from EntitySchema s where s.owner = :owner and s.entity = :entity and s.property = :property", EntitySchema.class)
                            .setParameter("owner", identityProvider.getUser())
                            .setParameter("entity", source.getClass())
                            .setParameter("property", entry.getKey())
                            .getSingleResult();

                    entitySchema.getVisibility().clear();

                    for (CategoryType category : categories) {
                        Category categoryEntity = entityManager.find(Category.class, category.getId());
                        entitySchema.getVisibility().add(categoryEntity);
                    }
                } catch (NoResultException e) {
                    EntitySchema schemaItem = new EntitySchema();
                    schemaItem.setEntity(source.getClass());
                    schemaItem.setOwner(identityProvider.getUser());
                    schemaItem.setProperty(entry.getKey());
                    for (CategoryType category : categories) {
                        Category categoryEntity = entityManager.find(Category.class, category.getId());
                        schemaItem.getVisibility().add(categoryEntity);
                    }
                    entityManager.persist(schemaItem);
                }
            }
        }
    }

    private static <S> Boolean isDirty(S source, BeanProperty<S, ?> propertySource) {
        Boolean dirty = false;
        if (source instanceof AbstractSchemaEntity schemaEntity) {
            JsonNode jsonNode = schemaEntity.getSchema().getProperties().get(propertySource.getKey());
            if (jsonNode != null) {
                dirty = jsonNode.getDirty();
                if (dirty == null) {
                    dirty = false;
                }
            }
        } else {
            dirty = true;
        }
        return dirty;
    }


}