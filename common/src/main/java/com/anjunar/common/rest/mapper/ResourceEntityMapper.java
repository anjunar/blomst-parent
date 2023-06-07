package com.anjunar.common.rest.mapper;

import com.anjunar.common.ddd.AbstractColumnRight;
import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.ddd.AbstractRight;
import com.anjunar.common.rest.api.Form;
import com.anjunar.common.rest.api.SecuredForm;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.link.WebURLBuilderFactory;
import com.anjunar.common.rest.mapper.annotations.MapperConverter;
import com.anjunar.common.rest.mapper.annotations.MapperConverterType;
import com.anjunar.common.rest.mapper.annotations.MapperPolymorphism;
import com.anjunar.common.rest.mapper.annotations.MapperVisibility;
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
import org.checkerframework.checker.units.qual.degrees;
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
                if (Objects.nonNull(polymorphism) && polymorphism.value().equals(getObjectFromProxy(source))) {
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
        return map(source, destinationClass, (JsonObject) ((JsonArray) table.getSchema().getProperties().get("rows")).getItems(), true);
    }

    public <S, D> D map(S source, Class<D> destinationClass) {
        return map(source, destinationClass, null, false);
    }

    public <S, D> D map(S source, Class<D> destinationClass, SecuredForm<?> securedForm) {
        return map(source, destinationClass, (JsonObject) securedForm.getSchema().getProperties().get("form"), false);
    }

    public <S, D> Form<D> map(S source, Form<D> form) {
        ResolvedType<? extends Form> typeResolver = TypeResolver.resolve(form.getClass());
        TypeToken<?> typeToken = typeResolver.getType().resolveType(form.getClass().getSuperclass().getTypeParameters()[0]);
        Class<D> rawType = (Class<D>) typeToken.getRawType();
        D result = map(source, rawType, (JsonObject) form.getSchema().getProperties().get("form"), false);
        form.setForm(result);
        return form;
    }

    public <S, D> SecuredForm<D> map(S source, SecuredForm<D> securedForm) {
        ResolvedType<? extends SecuredForm> typeResolver = TypeResolver.resolve(securedForm.getClass());
        TypeToken<?> typeToken = typeResolver.getType().resolveType(securedForm.getClass().getSuperclass().getTypeParameters()[0]);
        Class<D> rawType = (Class<D>) typeToken.getRawType();
        D result = map(source, rawType, (JsonObject) securedForm.getSchema().getProperties().get("form"), false);
        securedForm.setForm(result);
        loadFormSchema(source, securedForm);
        return securedForm;
    }

    public <S, D> D map(S source, Class<D> destinationClass, JsonObject jsonObject, boolean isTable) {
        D destination = getNewInstance(source, destinationClass);

        if (source instanceof OwnerProvider ownerProvider && Objects.nonNull(jsonObject)) {
            if (identityStore.getUser().equals(ownerProvider.getOwner())) {
                loadSchema((OwnerProvider) source, destinationClass, jsonObject);
            } else {
                for (Map.Entry<String, JsonNode> entry : jsonObject.getProperties().entrySet()) {
                    entry.getValue().setVisibility(null);
                }
            }
        }

        BeanModel<S> sourceModel = (BeanModel<S>) BeanIntrospector.create(source.getClass());
        BeanModel<D> destinationModel = (BeanModel<D>) BeanIntrospector.create(destination.getClass());

        for (BeanProperty<S, ?> sourceProperty : sourceModel.getProperties()) {
            BeanProperty<D, Object> destinationProperty = (BeanProperty<D, Object>) destinationModel.get(sourceProperty.getKey());

            if (Objects.nonNull(destinationProperty)) {
                Object sourcePropertyInstance = sourceProperty.apply(source);
                Object destinationPropertyInstance = destinationProperty.apply(destination);

                Class<? super Object> propertyType = destinationProperty.getType().getRawType();
                if (! Objects.nonNull(destinationPropertyInstance) && Collection.class.isAssignableFrom(propertyType)) {
                    Object collection = null;
                    if (propertyType.equals(Set.class)) {
                        collection = new HashSet<>();
                    }
                    if (propertyType.equals(List.class)) {
                        collection = new ArrayList<>();
                    }
                    if (propertyType.equals(Map.class)) {
                        collection = new HashMap<>();
                    }
                    if (Objects.nonNull(collection)) {
                        destinationProperty.accept(destination, collection);
                        destinationPropertyInstance = collection;
                    }
                }

                if (Objects.nonNull(sourcePropertyInstance)) {
                    if (securityProviders.stream().allMatch(securityProvider -> securityProvider.execute(source, sourceProperty, destination, destinationProperty))) {
                        MapperConverter mapperConverter = destinationProperty.getAnnotation(MapperConverter.class);
                        if (mapperConverter == null) {
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
                            MapperConverterType converter = getNewInstance(mapperConverter.value());
                            Object restEntity = converter.factory(sourcePropertyInstance);
                            destinationProperty.accept(destination, restEntity);
                        }
                    } else {
                        if (! isTable && Objects.nonNull(jsonObject)) {
                            jsonObject.remove(destinationProperty.getKey());
                        }
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

        MapperVisibility mapperVisibility = destinationProperty.getAnnotation(MapperVisibility.class);
        if (Objects.nonNull(mapperVisibility)) {
            if (! mapperVisibility.configurable()) {
                if (Objects.nonNull(jsonObject)) {
                    JsonNode jsonNode = jsonObject.getProperties().get(destinationProperty.getKey());
                    if (Objects.nonNull(jsonNode)) {
                        jsonNode.setVisibility(null);
                    }
                }
            }
        }

        if (sourcePropertyType.equals(destinationPropertyType)) {
            destinationProperty.accept(destination, sourcePropertyInstance);
        } else {
            if (UUID.class.isAssignableFrom(destinationPropertyType)) {
                if (sourcePropertyInstance instanceof AbstractEntity entity) {
                    destinationProperty.accept(destination, entity.getId());
                }
            } else {
                JsonObject jsonNode = null;
                if (Objects.nonNull(jsonObject)) {
                    jsonNode = (JsonObject) jsonObject.getProperties().get(sourceProperty.getKey());
                }
                Object restEntity = map(sourcePropertyInstance, destinationPropertyType, jsonNode, false);
                destinationProperty.accept(destination, restEntity);
            }
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
                JsonObject jsonNode = null;
                if (Objects.nonNull(jsonObject)) {
                    jsonNode = (JsonObject) jsonObject.getProperties().get(entry.getKey());
                }
                Object restEntity = map(entry.getKey(), destinationMapKeyType, jsonNode, false);
                if (sourceMapValueType.equals(destinationMapValueType)) {
                    destinationPropertyInstance.put(restEntity, entry.getValue());
                } else {
                    Object restEntity2 = map(entry.getValue(), destinationMapValueType, jsonNode, false);
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
                JsonObject items = null;
                if (Objects.nonNull(jsonObject)) {
                    JsonNode jsonNode = jsonObject.getProperties().get(sourceProperty.getKey());
                    JsonArray jsonArray = (JsonArray) jsonNode;
                    items = (JsonObject) jsonArray.getItems();
                }
                Object restEntity = map(element, destinationCollectionType, items, false);
                destinationPropertyInstance.add(restEntity);
            }
        }
    }

    private <S, D> void loadFormSchema(S source, SecuredForm<D> securedForm) {
        JsonObject jsonObject = securedForm.find("form", JsonObject.class);
        jsonObject.setVisibility(true);
    }


    private <S extends AbstractEntity> void loadSchema(OwnerProvider source, Class<?> destinationClass, JsonObject jsonObject) {
        BeanModel<?> destinationModel = BeanIntrospector.create(destinationClass);
        for (Map.Entry<String, JsonNode> entry : jsonObject.getProperties().entrySet()) {
            BeanProperty<?, ?> destinationProperty = destinationModel.get(entry.getKey());
            MapperVisibility visibility = destinationProperty.getAnnotation(MapperVisibility.class);
            if (Objects.nonNull(visibility)) {
                entry.getValue().setVisibility(true);
            }
        }
    }

}
