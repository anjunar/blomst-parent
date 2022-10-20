package com.anjunar.common.rest.mapper;

import com.anjunar.common.ddd.AbstractColumnRight;
import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.ddd.AbstractRight;
import com.anjunar.common.rest.api.AbstractSchemaEntity;
import com.anjunar.common.rest.api.Form;
import com.anjunar.common.rest.api.SecuredForm;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.mapper.annotations.MapperConverter;
import com.anjunar.common.rest.mapper.annotations.MapperConverterType;
import com.anjunar.common.rest.mapper.annotations.MapperPolymorphism;
import com.anjunar.common.rest.mapper.entity.SecurityProvider;
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
import com.anjunar.introspector.type.TypeResolver;
import com.anjunar.introspector.type.resolved.ResolvedType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.google.common.base.Strings;
import com.google.common.reflect.TypeToken;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.hibernate.proxy.HibernateProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@ApplicationScoped
@SuppressWarnings({"unchecked", "UnstableApiUsage", "rawtypes"})
public class ResourceEntityMapper {

    private static final Logger log = LoggerFactory.getLogger(ResourceEntityMapper.class);

    private final IdentityStore identityStore;

    private final EntityManager entityManager;

    private final Instance<SecurityProvider> securityProviders;

    @Inject
    public ResourceEntityMapper(IdentityStore identityStore, EntityManager entityManager, Instance<SecurityProvider> securityProviders) {
        this.identityStore = identityStore;
        this.entityManager = entityManager;
        this.securityProviders = securityProviders;
    }

    public ResourceEntityMapper() {
        this(null, null, null);
    }

    static <S, D> D getNewInstance(Class<D> destinationClass) {
        try {
            return destinationClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    static <S, D> D getNewInstance(S source, Class<D> destinationClass) {
        BeanModel<D> destinationModel = BeanIntrospector.create(destinationClass);
        JsonSubTypes jsonSubTypes = destinationModel.getAnnotation(JsonSubTypes.class);
        if (Objects.nonNull(jsonSubTypes)) {
            JsonSubTypes.Type[] types = jsonSubTypes.value();
            for (JsonSubTypes.Type type : types) {
                Class<?> value = type.value();
                BeanModel<?> typeModel = BeanIntrospector.create(value);
                MapperPolymorphism polymorphism = typeModel.getAnnotation(MapperPolymorphism.class);
                if (polymorphism.value().equals(getObjectFromProxy(source))) {
                    return (D) getNewInstance(value);
                }
            }
        }
        return getNewInstance(destinationClass);
    }

    static Class<?> getObjectFromProxy(Object object) {
        if (object instanceof HibernateProxy proxy) {
            return proxy.getHibernateLazyInitializer().getImplementation().getClass();
        }
        return object.getClass();
    }

    public IdentityStore identityProvider() {
        return identityStore;
    }

    public EntityManager entityManager() {
        return entityManager;
    }

    public <S, D> D map(S source, Class<D> destinationClass, Table table) {
        return map(source, destinationClass, (JsonObject) ((JsonArray) table.getSchema().getProperties().get("rows")).getItems());
    }

    public <S, D> D map(S source, Class<D> destinationClass, Form<?> form, String property) {
        return map(source, destinationClass, (JsonObject) ((JsonObject) form.getSchema().getProperties().get("form")).getProperties().get(property));
    }

    public <S, D> D map(S source, Class<D> destinationClass, SecuredForm<?> table) {
        return map(source, destinationClass, (JsonObject) table.getSchema().getProperties().get("form"));
    }

    public <S, D> Form<D> map(S source, Form<D> form) {
        ResolvedType<? extends Form> typeResolver = TypeResolver.resolve(form.getClass());
        TypeToken<?> typeToken = typeResolver.getType().resolveType(form.getClass().getSuperclass().getTypeParameters()[0]);
        Class<D> rawType = (Class<D>) typeToken.getRawType();
        D result = map(source, rawType, (JsonObject) form.getSchema().getProperties().get("form"));
        form.setForm(result);
        return form;
    }

    public <S, D> SecuredForm<D> map(S source, SecuredForm<D> securedForm) {
        ResolvedType<? extends SecuredForm> typeResolver = TypeResolver.resolve(securedForm.getClass());
        TypeToken<?> typeToken = typeResolver.getType().resolveType(securedForm.getClass().getSuperclass().getTypeParameters()[0]);
        Class<D> rawType = (Class<D>) typeToken.getRawType();
        D result = map(source, rawType, (JsonObject) securedForm.getSchema().getProperties().get("form"));
        securedForm.setForm(result);
        loadFormSchema(source, securedForm);
        return securedForm;
    }

    public <S, D> D map(S source, Class<D> destinationClass, JsonObject jsonObject) {
        D destination = getNewInstance(source, destinationClass);

        if (source instanceof OwnerProvider ownerProvider) {
            if (identityStore.getUser().equals(ownerProvider.getOwner())) {
                loadSchema((OwnerProvider) source, jsonObject);
            } else {
                for (Map.Entry<String, JsonNode> entry : jsonObject.getProperties().entrySet()) {
                    entry.getValue().setVisibility(null);
                }
            }
        }

        BeanModel<S> sourceModel = (BeanModel<S>) BeanIntrospector.create(source.getClass());
        BeanModel<D> destinationModel = BeanIntrospector.create(destinationClass);

        for (BeanProperty<S, ?> sourceProperty : sourceModel.getProperties()) {
            BeanProperty<D, Object> destinationProperty = (BeanProperty<D, Object>) destinationModel.get(sourceProperty.getKey());

            if (Objects.nonNull(destinationProperty)) {
                Object sourcePropertyInstance = sourceProperty.apply(source);
                Object destinationPropertyInstance = destinationProperty.apply(destination);

                if (Objects.nonNull(sourcePropertyInstance)) {
                    if (securityProviders.stream().allMatch(securityProvider -> securityProvider.execute(source, sourceProperty, destination, destinationProperty))) {
                        switch (sourcePropertyInstance) {
                            case Collection<?> collection -> processCollection(
                                    (Collection<Object>) sourcePropertyInstance,
                                    sourceProperty,
                                    (Collection<Object>) destinationPropertyInstance,
                                    destinationProperty,
                                    jsonObject
                            );
                            case Map<?, ?> map -> processMap(
                                    (Map<Object, Object>) sourcePropertyInstance,
                                    sourceProperty,
                                    (Map<Object, Object>) destinationPropertyInstance,
                                    destinationProperty,
                                    jsonObject
                            );
                            default -> processBean(
                                    sourcePropertyInstance,
                                    sourceProperty,
                                    destinationPropertyInstance,
                                    destinationProperty,
                                    destination,
                                    jsonObject
                            );
                        }
                    } else {
                        jsonObject.remove(destinationProperty.getKey());
                    }
                }
            }
        }
        return destination;
    }

    private <S, D> void processBean(Object sourcePropertyInstance,
                                    BeanProperty<S, ?> sourceProperty,
                                    Object destinationPropertyInstance,
                                    BeanProperty<D, Object> destinationProperty,
                                    D destination,
                                    JsonObject jsonObject) {
        Class<?> sourcePropertyType = sourceProperty.getType().getRawType();
        Class<?> destinationPropertyType = destinationProperty.getType().getRawType();

        MapperConverter mapperConverter = destinationProperty.getAnnotation(MapperConverter.class);
        if (mapperConverter == null) {
            if (sourcePropertyType.equals(destinationPropertyType)) {
                destinationProperty.accept(destination, sourcePropertyInstance);
            } else {
                if (UUID.class.isAssignableFrom(destinationPropertyType)) {
                    if (sourcePropertyInstance instanceof AbstractEntity entity) {
                        destinationProperty.accept(destination, entity.getId());
                    }
                } else {
                    JsonObject jsonNode = (JsonObject) jsonObject.getProperties().get(sourceProperty.getKey());
                    Object restEntity = map(sourcePropertyInstance, destinationPropertyType, jsonNode);
                    destinationProperty.accept(destination, restEntity);
                }
            }
        } else {
            MapperConverterType converter = getNewInstance(mapperConverter.value());
            Object restEntity = converter.factory(sourcePropertyInstance);
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

        for (Map.Entry<Object, Object> entry : sourcePropertyInstance.entrySet()) {
            if (sourceMapKeyType.equals(destinationMapKeyType)) {
                destinationPropertyInstance.put(entry.getKey(), entry.getValue());
            } else {
                JsonObject jsonNode = (JsonObject) jsonObject.getProperties().get(entry.getKey());
                Object restEntity = map(entry.getKey(), destinationMapKeyType, jsonNode);

                if (sourceMapValueType.equals(destinationMapValueType)) {
                    destinationPropertyInstance.put(restEntity, entry.getValue());
                } else {
                    Object restEntity2 = map(entry.getValue(), destinationMapValueType, jsonNode);
                    destinationPropertyInstance.put(restEntity, restEntity2);
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

        for (Object element : sourcePropertyInstance) {
            if (sourceCollectionType.equals(destinationCollectionType)) {
                destinationPropertyInstance.add(element);
            } else {
                JsonNode jsonNode = jsonObject.getProperties().get(sourceProperty.getKey());
                JsonArray jsonArray = (JsonArray) jsonNode;
                Object restEntity = map(element, destinationCollectionType, (JsonObject) jsonArray.getItems());
                destinationPropertyInstance.add(restEntity);
            }
        }
    }

    private <S, D> void loadFormSchema(S source, SecuredForm<D> securedForm) {
        BeanModel<?> model = BeanIntrospector.create(source.getClass());
        String entityName = null;
        Entity annotation = model.getAnnotation(Entity.class);
        if (Objects.nonNull(annotation) && !Strings.isNullOrEmpty(annotation.name())) {
            entityName = annotation.name();
        } else {
            entityName = source.getClass().getSimpleName();
        }

        try {
            JsonObject jsonObject = securedForm.find("form", JsonObject.class);
            Set<CategoryType> categories = jsonObject.getVisibility();
            EntityManager entityManager = entityManager();

            AbstractRight<?> right = entityManager.createQuery("select s from " + entityName + "Right s where s.source = :source", AbstractRight.class)
                    .setParameter("source", source)
                    .getSingleResult();

            for (Category category : right.getCategories()) {
                CategoryType categoryType = map(category, CategoryType.class,(JsonObject) null);
                categories.add(categoryType);
            }
        } catch (NoResultException e) {
            try {
                Class<?> right = Class.forName(source.getClass().getPackageName() + "." + source.getClass().getSimpleName() + "Right");
                AbstractRight schemaItem = (AbstractRight) right.getDeclaredConstructor().newInstance();
                schemaItem.setSource(source);
                entityManager.persist(schemaItem);
            } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
                     IllegalAccessException | NoSuchMethodException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    private <S extends AbstractEntity> void loadSchema(OwnerProvider source, JsonObject jsonObject) {
        JsonObject schema = jsonObject;

        BeanModel<?> model = BeanIntrospector.create(source.getClass());
        String entityName = null;
        Entity annotation = model.getAnnotation(Entity.class);
        if (Objects.nonNull(annotation) && !Strings.isNullOrEmpty(annotation.name())) {
            entityName = annotation.name();
        } else {
            entityName = source.getClass().getSimpleName();
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

                try {
                    if (entry.getValue().getVisibility() != null) {
                        Set<CategoryType> categories = entry.getValue().getVisibility();
                        EntityManager entityManager = entityManager();

                        AbstractRight<?> right = entityManager.createQuery("select s from " + entityName + "Right s where s.source = :source and s.column = :column", AbstractRight.class)
                                .setParameter("source", source)
                                .setParameter("column", columnName)
                                .getSingleResult();

                        for (Category category : right.getCategories()) {
                            CategoryType categoryType = map(category, CategoryType.class, (JsonObject) null);
                            categories.add(categoryType);
                        }
                    }
                } catch (NoResultException e) {
                    try {
                        Class<?> right = Class.forName(source.getClass().getPackageName() + "." + source.getClass().getSimpleName() + "Right");
                        AbstractColumnRight schemaItem = (AbstractColumnRight) right.getDeclaredConstructor().newInstance();
                        schemaItem.setSource(source);
                        schemaItem.setColumn(columnName);
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
