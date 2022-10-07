package com.anjunar.common.rest.mapper;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.ddd.AbstractRight;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.AbstractSchemaEntity;
import com.anjunar.common.rest.mapper.annotations.MapperConverter;
import com.anjunar.common.rest.mapper.annotations.MapperConverterType;
import com.anjunar.common.rest.mapper.annotations.MapperPermanent;
import com.anjunar.common.rest.mapper.rest.SecurityProvider;
import com.anjunar.common.rest.schema.CategoryType;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.security.*;
import com.anjunar.introspector.bean.BeanIntrospector;
import com.anjunar.introspector.bean.BeanModel;
import com.anjunar.introspector.bean.BeanProperty;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.persistence.Column;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@ApplicationScoped
@SuppressWarnings({"unchecked", "UnstableApiUsage", "rawtypes"})
public class ResourceRestMapper {

    private static final Logger log = LoggerFactory.getLogger(ResourceRestMapper.class);

    private final IdentityStore identityStore;

    private final EntityManager entityManager;

    private final Instance<SecurityProvider> securityProviders;

    @Inject
    public ResourceRestMapper(IdentityStore identityStore, EntityManager entityManager, Instance<SecurityProvider> securityProviders) {
        this.identityStore = identityStore;
        this.entityManager = entityManager;
        this.securityProviders = securityProviders;
    }

    public ResourceRestMapper() {
        this(null, null, null);
    }

    public <S extends AbstractSchemaEntity, D> D map(S source, Class<D> destinationClass) {
        return map(source, destinationClass, false, false);
    }

    public <S extends AbstractSchemaEntity, D> D map(S source, Class<D> destinationClass, boolean isDirty, boolean loadOnly) {
        D destination;
        if (source instanceof AbstractRestEntity restEntity) {
            UUID id = restEntity.getId();
            if (id == null) {
                destination = getNewInstance(destinationClass);
            } else {
                destination = entityManager.find(destinationClass, id);
            }
        } else {
            destination = getNewInstance(destinationClass);
        }

        if (destination instanceof AbstractEntity entity) {
            entity.setIdentityStore(identityStore);
        }

        if (loadOnly) {
            return destination;
        }

        BeanModel<S> sourceModel = (BeanModel<S>) BeanIntrospector.create(source.getClass());
        BeanModel<D> destinationModel = BeanIntrospector.create(destinationClass);

        for (BeanProperty<S, ?> sourceProperty : sourceModel.getProperties()) {
            BeanProperty<D, Object> destinationProperty = (BeanProperty<D, Object>) destinationModel.get(sourceProperty.getKey());

            if (Objects.nonNull(destinationProperty)) {
                Object sourcePropertyInstance = sourceProperty.apply(source);
                Object destinationPropertyInstance = destinationProperty.apply(destination);

                if (Objects.nonNull(sourcePropertyInstance) && (isDirty(source, sourceProperty) || isDirty)) {
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
                                    source,
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

        if (destination instanceof OwnerProvider ownerProvider) {
            if (identityStore.getUser().equals(ownerProvider.getOwner())) {
                saveSchema(source, destination);
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

    private <S extends AbstractSchemaEntity, D> void processBean(S source,
                                                                 Object sourcePropertyInstance,
                                                                 BeanProperty<S, ?> sourceProperty,
                                                                 Object destinationPropertyInstance,
                                                                 BeanProperty<D, Object> destinationProperty, D destination) {
        Class<?> sourcePropertyType = sourceProperty.getType().getRawType();
        Class<?> destinationPropertyType = destinationProperty.getType().getRawType();

        MapperPermanent mapperPermanent = sourceProperty.getAnnotation(MapperPermanent.class);
        MapperConverter mapperConverter = sourceProperty.getAnnotation(MapperConverter.class);
        if (mapperConverter == null) {
            if (sourcePropertyType.equals(destinationPropertyType)) {
                if (!destinationProperty.isReadOnly()) {
                    destinationProperty.accept(destination, sourcePropertyInstance);
                }
            } else {
                if (UUID.class.isAssignableFrom(sourcePropertyType)) {
                    UUID uuid = (UUID) sourcePropertyInstance;
                    Object entity = entityManager.find(destinationPropertyType, uuid);
                    destinationProperty.accept(destination, entity);
                } else {
                    AbstractSchemaEntity entity = (AbstractSchemaEntity) sourcePropertyInstance;
                    Object restEntity = map(entity, (Class<?>) destinationPropertyType, isDirty(source, sourceProperty), mapperPermanent != null);
                    destinationProperty.accept(destination, restEntity);
                }
            }
        } else {
            MapperConverterType converter = getNewInstance(mapperConverter.value());
            Object restEntity = converter.updater(sourcePropertyInstance);
            destinationProperty.accept(destination, restEntity);
        }
    }

    private <S extends AbstractSchemaEntity, D> void processMap(Map<Object, Object> sourcePropertyInstance,
                                                                BeanProperty<S, ?> sourceProperty,
                                                                Map<Object, Object> destinationPropertyInstance,
                                                                BeanProperty<D, Object> destinationProperty) {
        MapperPermanent mapperPermanent = sourceProperty.getAnnotation(MapperPermanent.class);

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
            if (sourceMapKeyType.equals(destinationMapKeyType)) {
                destinationPropertyInstance.put(entry.getKey(), entry.getValue());
            } else {
                AbstractSchemaEntity restKeyEntity = (AbstractSchemaEntity) entry.getKey();
                Object keyEntity = map(restKeyEntity, (Class<?>) destinationMapKeyType, false, mapperPermanent != null);
                if (sourceMapValueType.equals(destinationMapValueType)) {
                    destinationPropertyInstance.put(keyEntity, entry.getValue());
                } else {
                    AbstractSchemaEntity restValueEntity = (AbstractSchemaEntity) entry.getValue();
                    Object valueEntity = map(restValueEntity, (Class<?>) destinationMapValueType, false, mapperPermanent != null);
                    destinationPropertyInstance.put(keyEntity, valueEntity);
                }
            }
        }
    }

    private <S extends AbstractSchemaEntity, D> void processCollection(Collection<Object> sourcePropertyInstance,
                                                                       BeanProperty<S, ?> sourceProperty,
                                                                       Collection<Object> destinationPropertyInstance,
                                                                       BeanProperty<D, Object> destinationProperty) {
        MapperPermanent mapperPermanent = sourceProperty.getAnnotation(MapperPermanent.class);

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
            if (sourceCollectionType.equals(destinationCollectionType)) {
                destinationPropertyInstance.add(element);
            } else {
                AbstractSchemaEntity restEntity = (AbstractSchemaEntity) element;
                Object entity = map(restEntity, (Class<?>) destinationCollectionType, false, mapperPermanent != null);
                destinationPropertyInstance.add(entity);
            }
        }
    }

    private <S extends AbstractSchemaEntity, D> void saveSchema(S source, D destination) {
        JsonObject schema = source.getSchema();

        BeanModel<?> model = BeanIntrospector.create(destination.getClass());
        String tableName = null;
        Table annotation = model.getAnnotation(Table.class);
        if (Objects.nonNull(annotation)) {
            tableName = annotation.name();
        } else {
            tableName = destination.getClass().getSimpleName();
        }

        for (Map.Entry<String, JsonNode> entry : schema.getProperties().entrySet()) {
            String columnName = null;
            BeanProperty<?, ?> property = model.get(entry.getKey());
            if (Objects.nonNull(property)) {
                Column column = property.getAnnotation(Column.class);
                if (Objects.nonNull(column)) {
                    columnName = column.name();
                } else {
                    columnName = entry.getKey();
                }
                if (entry.getValue().getVisibility() != null) {
                    Set<CategoryType> categories = entry.getValue().getVisibility();
                    try {
                        AbstractRight<?> right = entityManager.createQuery("select s from " + tableName + "Right s where s.source = :source and s.column = :column", AbstractRight.class)
                                .setParameter("source", destination)
                                .setParameter("column", columnName)
                                .getSingleResult();

                        right.getCategories().clear();

                        for (CategoryType category : categories) {
                            Category categoryEntity = entityManager.find(Category.class, category.getId());
                            right.getCategories().add(categoryEntity);
                        }
                    } catch (NoResultException e) {
                        try {
                            Class<?> right = Class.forName(destination.getClass().getPackageName() + "." + destination.getClass().getSimpleName() + "Right");
                            AbstractRight schemaItem = (AbstractRight) right.getDeclaredConstructor().newInstance();
                            schemaItem.setSource((AbstractEntity) destination);
                            schemaItem.setColumn(columnName);
                            for (CategoryType category : categories) {
                                Category categoryEntity = entityManager.find(Category.class, category.getId());
                                schemaItem.getCategories().add(categoryEntity);
                            }
                            entityManager.persist(schemaItem);
                        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
                                 IllegalAccessException | NoSuchMethodException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        }
    }

    private static <S> Boolean isDirty(S source, BeanProperty<S, ?> propertySource) {
        MapperPermanent mapperPermanent = propertySource.getAnnotation(MapperPermanent.class);
        if (Objects.nonNull(mapperPermanent)) {
            return true;
        }
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
