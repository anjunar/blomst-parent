package com.anjunar.common.rest.mapper;

import com.anjunar.common.ddd.AbstractColumnRight;
import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.ddd.AbstractRight;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.Form;
import com.anjunar.common.rest.api.SecuredForm;
import com.anjunar.common.rest.mapper.annotations.MapperConverter;
import com.anjunar.common.rest.mapper.annotations.MapperConverterType;
import com.anjunar.common.rest.mapper.rest.SecurityProvider;
import com.anjunar.common.rest.schema.CategoryType;
import com.anjunar.common.rest.schema.schema.JsonArray;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.security.Category;
import com.anjunar.common.security.IdentityStore;
import com.anjunar.common.security.OwnerProvider;
import com.anjunar.introspector.bean.BeanIntrospector;
import com.anjunar.introspector.bean.BeanModel;
import com.anjunar.introspector.bean.BeanProperty;
import com.google.common.base.Strings;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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

    public <S, D> D map(Form<S> source, Class<D> destinationClass) {
        return map(source.getForm(), destinationClass, (JsonObject) source.getSchema().getProperties().get("form"), false, false);
    }

    public <S, D> D map(SecuredForm<S> source, Class<D> destinationClass) {
        D result = map(source.getForm(), destinationClass, (JsonObject) source.getSchema().getProperties().get("form"), false, false);
        saveFormSchema(source, result);
        return result;
    }

    public <S, D> D map(S source, Class<D> destinationClass, JsonObject jsonNode, boolean isDirty, boolean loadOnly) {
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

                if (Objects.nonNull(sourcePropertyInstance) && (isDirty(jsonNode, sourceProperty) || isDirty)) {
                    if (securityProviders.stream().anyMatch(securityProvider -> securityProvider.execute(source, sourceProperty, destination, destinationProperty))) {
                        switch (sourcePropertyInstance) {
                            case Collection<?> collection -> processCollection(
                                    (Collection<Object>) sourcePropertyInstance,
                                    sourceProperty,
                                    (Collection<Object>) destinationPropertyInstance,
                                    destinationProperty,
                                    jsonNode
                            );
                            case Map<?, ?> map -> processMap(
                                    (Map<Object, Object>) sourcePropertyInstance,
                                    sourceProperty,
                                    (Map<Object, Object>) destinationPropertyInstance,
                                    destinationProperty,
                                    jsonNode
                            );
                            default -> processBean(
                                    source,
                                    sourcePropertyInstance,
                                    sourceProperty,
                                    destinationPropertyInstance,
                                    destinationProperty,
                                    destination,
                                    jsonNode
                            );
                        }
                    }
                }
            }
        }

        if (destination instanceof OwnerProvider ownerProvider) {
            if (identityStore.getUser().equals(ownerProvider.getOwner())) {
                saveSchema(source, destination, jsonNode);
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

    private <S, D> void processBean(S source,
                                    Object sourcePropertyInstance,
                                    BeanProperty<S, ?> sourceProperty,
                                    Object destinationPropertyInstance,
                                    BeanProperty<D, Object> destinationProperty,
                                    D destination,
                                    JsonObject jsonObject) {
        Class<?> sourcePropertyType = sourceProperty.getType().getRawType();
        Class<?> destinationPropertyType = destinationProperty.getType().getRawType();

        boolean mapperLoadOnly = AbstractEntity.class.isAssignableFrom(destinationPropertyType);
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
                    JsonNode jsonNode = jsonObject.getProperties().get(sourceProperty.getKey());
                    if (jsonNode instanceof JsonObject jsonObjectX) {
                        Object restEntity = map(sourcePropertyInstance, (Class<?>) destinationPropertyType, jsonObjectX, isDirty(jsonObjectX, sourceProperty), mapperLoadOnly);
                        destinationProperty.accept(destination, restEntity);
                    } else {
                        Object restEntity = map(sourcePropertyInstance, (Class<?>) destinationPropertyType, new JsonObject(), true, mapperLoadOnly);
                        destinationProperty.accept(destination, restEntity);
                    }
                }
            }
        } else {
            MapperConverterType converter = getNewInstance(mapperConverter.value());
            Object restEntity = converter.updater(sourcePropertyInstance, source);
            destinationProperty.accept(destination, restEntity);
        }
    }

    private <S, D> void processMap(Map<Object, Object> sourcePropertyInstance,
                                   BeanProperty<S, ?> sourceProperty,
                                   Map<Object, Object> destinationPropertyInstance,
                                   BeanProperty<D, Object> destinationProperty,
                                   JsonObject jsonObject) {
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

        boolean mapperLoadOnlyKey = AbstractEntity.class.isAssignableFrom(destinationMapKeyType);
        boolean mapperLoadOnlyValue = AbstractEntity.class.isAssignableFrom(destinationMapValueType);

        for (Map.Entry<Object, Object> entry : sourcePropertyInstance.entrySet()) {
            if (sourceMapKeyType.equals(destinationMapKeyType)) {
                destinationPropertyInstance.put(entry.getKey(), entry.getValue());
            } else {
                JsonArray jsonNode = (JsonArray) jsonObject.getProperties().get(sourceProperty.getKey());
                Object keyEntity = map(entry.getKey(), (Class<?>) destinationMapKeyType, (JsonObject) jsonNode.getItems(), false, mapperLoadOnlyKey);
                if (sourceMapValueType.equals(destinationMapValueType)) {
                    destinationPropertyInstance.put(keyEntity, entry.getValue());
                } else {
                    Object valueEntity = map(entry.getValue(), (Class<?>) destinationMapValueType, (JsonObject) jsonNode.getItems(), false, mapperLoadOnlyValue);
                    destinationPropertyInstance.put(keyEntity, valueEntity);
                }
            }
        }
    }

    private <S, D> void processCollection(Collection<Object> sourcePropertyInstance,
                                          BeanProperty<S, ?> sourceProperty,
                                          Collection<Object> destinationPropertyInstance,
                                          BeanProperty<D, Object> destinationProperty,
                                          JsonObject jsonObject) {

        Class<?> sourceCollectionType = sourceProperty
                .getType()
                .resolveType(Collection.class.getTypeParameters()[0])
                .getRawType();

        Class<?> destinationCollectionType = destinationProperty
                .getType()
                .resolveType(Collection.class.getTypeParameters()[0])
                .getRawType();

        boolean mapperLoadOnly = AbstractEntity.class.isAssignableFrom(destinationCollectionType);

        destinationPropertyInstance.clear();

        for (Object element : sourcePropertyInstance) {
            if (sourceCollectionType.equals(destinationCollectionType)) {
                destinationPropertyInstance.add(element);
            } else {
                JsonArray jsonNode = (JsonArray) jsonObject.getProperties().get(sourceProperty.getKey());
                Object entity = map(element, (Class<?>) destinationCollectionType, (JsonObject) jsonNode.getItems(), false, mapperLoadOnly);
                destinationPropertyInstance.add(entity);
            }
        }
    }

    private <S, D> void saveFormSchema(SecuredForm<S> source, D destination) {
        JsonObject form = source.find("form", JsonObject.class);
        Set<CategoryType> visibility = form.getVisibility();

        BeanModel<?> model = BeanIntrospector.create(destination.getClass());
        String entityName = null;
        Entity annotation = model.getAnnotation(Entity.class);
        if (Objects.nonNull(annotation) && !Strings.isNullOrEmpty(annotation.name())) {
            entityName = annotation.name();
        } else {
            entityName = destination.getClass().getSimpleName();
        }

        try {
            AbstractRight right = entityManager.createQuery("select r from " + entityName + "Right r where r.source = :source", AbstractRight.class)
                    .setParameter("source", destination)
                    .getSingleResult();

            right.getCategories().clear();

            for (CategoryType category : visibility) {
                Category categoryEntity = entityManager.find(Category.class, category.getId());
                right.getCategories().add(categoryEntity);
            }
        } catch (NoResultException e) {
            try {
                Class<?> right = Class.forName(destination.getClass().getPackageName() + "." + destination.getClass().getSimpleName() + "Right");
                AbstractRight schemaItem = (AbstractRight) right.getDeclaredConstructor().newInstance();
                schemaItem.setSource(destination);
                for (CategoryType category : visibility) {
                    Category categoryEntity = entityManager.find(Category.class, category.getId());
                    schemaItem.getCategories().add(categoryEntity);
                }
                entityManager.persist(schemaItem);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                     InvocationTargetException | NoSuchMethodException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    private <S, D> void saveSchema(S source, D destination, JsonObject jsonNode) {
        JsonObject schema = jsonNode;

        BeanModel<?> model = BeanIntrospector.create(destination.getClass());
        String entityName = null;
        Entity annotation = model.getAnnotation(Entity.class);
        if (Objects.nonNull(annotation) && !Strings.isNullOrEmpty(annotation.name())) {
            entityName = annotation.name();
        } else {
            entityName = destination.getClass().getSimpleName();
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
                        AbstractRight<?> right = entityManager.createQuery("select s from " + entityName + "Right s where s.source = :source and s.column = :column", AbstractRight.class)
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
                            AbstractColumnRight schemaItem = (AbstractColumnRight) right.getDeclaredConstructor().newInstance();
                            schemaItem.setSource(destination);
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

    private static <S> Boolean isDirty(JsonObject jsonObject, BeanProperty<S, ?> propertySource) {
        Boolean dirty = false;
        JsonNode jsonNode = jsonObject.getProperties().get(propertySource.getKey());
        if (jsonNode != null) {
            dirty = jsonNode.getDirty();
            if (dirty == null) {
                dirty = false;
            }
        }
        return dirty;
    }


}
