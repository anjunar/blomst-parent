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

    public <S, D> D map(S source, Class<D> destinationClass) {
        return map(source, destinationClass, false);
    }

    public <S, D> D map(S source, Class<D> destinationClass, boolean loadOnly) {
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

                if (Objects.nonNull(sourcePropertyInstance)) {
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
                                    D destination) {
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
                    Object restEntity = map(sourcePropertyInstance, (Class<?>) destinationPropertyType, mapperLoadOnly);
                    destinationProperty.accept(destination, restEntity);
                }
            }
        } else {
            MapperConverterType converter = getNewInstance(mapperConverter.value());
            Object restEntity = converter.updater(sourcePropertyInstance, destination);
            if (destinationProperty.isReadOnly()) {
                // No Operation because it seems to be a Collection
            } else {
                destinationProperty.accept(destination, restEntity);
            }
        }
    }

    private <S, D> void processMap(Map<Object, Object> sourcePropertyInstance,
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

        boolean mapperLoadOnlyKey = AbstractEntity.class.isAssignableFrom(destinationMapKeyType);
        boolean mapperLoadOnlyValue = AbstractEntity.class.isAssignableFrom(destinationMapValueType);

        for (Map.Entry<Object, Object> entry : sourcePropertyInstance.entrySet()) {
            if (sourceMapKeyType.equals(destinationMapKeyType)) {
                destinationPropertyInstance.put(entry.getKey(), entry.getValue());
            } else {
                Object valueEntity = map(entry.getValue(), (Class<?>) destinationMapValueType, mapperLoadOnlyValue);
                destinationPropertyInstance.put(entry.getKey(), valueEntity);
            }
        }
    }

    private <S, D> void processCollection(Collection<Object> sourcePropertyInstance,
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

        boolean mapperLoadOnly = AbstractEntity.class.isAssignableFrom(destinationCollectionType);

        destinationPropertyInstance.clear();

        for (Object element : sourcePropertyInstance) {
            if (sourceCollectionType.equals(destinationCollectionType)) {
                destinationPropertyInstance.add(element);
            } else {
                Object entity = map(element, (Class<?>) destinationCollectionType, mapperLoadOnly);
                destinationPropertyInstance.add(entity);
            }
        }
    }

}
