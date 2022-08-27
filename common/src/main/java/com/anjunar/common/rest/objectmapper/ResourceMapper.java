package com.anjunar.common.rest.objectmapper;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.AbstractSchemaEntity;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.introspector.bean.BeanIntrospector;
import com.anjunar.introspector.bean.BeanModel;
import com.anjunar.introspector.bean.BeanProperty;
import jakarta.enterprise.inject.spi.CDI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@SuppressWarnings({"unchecked", "UnstableApiUsage", "rawtypes"})
public class ResourceMapper {

    private static final Logger log = LoggerFactory.getLogger(ResourceMapper.class);

    private final NewInstanceProvider newInstanceProvider;

    private final IdProvider idProvider;

    public ResourceMapper(NewInstanceProvider newInstanceProvider, IdProvider idProvider) {
        this.newInstanceProvider = newInstanceProvider;
        this.idProvider = idProvider;
    }

    public ResourceMapper() {
        this(
                (id, sourceClass) -> {
                    try {
                        return sourceClass.getDeclaredConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                             NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                },
                source -> null);
    }

    public ResourceMapper(NewInstanceProvider newInstanceProvider) {
        this(
                newInstanceProvider,
                source -> {
                    if (source instanceof AbstractRestEntity) {
                        return ((AbstractRestEntity) source).getId();
                    }
                    return null;
                }
        );
    }

    public <S, D> D map(S source, Class<D> destinationClass) {
        if (destinationClass.isPrimitive() || source.getClass().equals(destinationClass)) {
            return (D) source;
        }

        try {
            boolean isNew = false;
            D destinationInstance = newInstance(source, destinationClass);

            if (destinationInstance == null) {
                destinationInstance = destinationClass.getDeclaredConstructor().newInstance();
                isNew = true;
            }

            BeanModel<S> beanModelSource = (BeanModel<S>) BeanIntrospector.create(source.getClass());
            BeanModel<D> beanModelDestination = BeanIntrospector.create(destinationClass);

            for (BeanProperty<S, ?> propertySource : beanModelSource.getProperties()) {
                Boolean dirty = isDirty(source, propertySource);

                if (isNew || dirty) {
                    BeanProperty<D, Object> propertyDestination = (BeanProperty<D, Object>) beanModelDestination.get(propertySource.getKey());

                    if ((propertyDestination != null && ! propertyDestination.isReadOnly()) || propertyDestination != null && propertyDestination.getType().isSubtypeOf(Collection.class)) {
                        Object sourcePropertyInstance = propertySource.apply(source);

                        if (sourcePropertyInstance != null) {
                            switch (sourcePropertyInstance) {
                                case List<?> list -> initializeList(destinationInstance, propertySource, propertyDestination, list);
                                case Set<?> set -> initializeSet(destinationInstance, propertySource, propertyDestination, set);
                                case Map map -> initializeMap(destinationInstance, propertySource, propertyDestination, map);
                                default -> initializeBean(source, destinationInstance, propertySource, propertyDestination, sourcePropertyInstance);
                            }
                        }
                    }
                }
            }
            return destinationInstance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    private <S, D> D newInstance(S source, Class<D> destinationClass) {
        if (source instanceof AbstractRestEntity) {
            Object id = idProvider.findID(source);
            if (id != null) {
                return (D) newInstanceProvider.execute(id, destinationClass);
            }
        }
        return null;
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

    private <S, D> void initializeBean(S source, D destinationInstance, BeanProperty<S, ?> propertySource, BeanProperty<D, Object> propertyDestination, Object sourcePropertyInstance) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        MapperConverter mapperDestinationAnnotation = propertyDestination.getAnnotation(MapperConverter.class);
        if (mapperDestinationAnnotation == null) {
            MapperConverter mapperSourceAnnotation = propertySource.getAnnotation(MapperConverter.class);
            if (mapperSourceAnnotation == null) {
                MapperSecurity securityAnnotationDestination = propertyDestination.getAnnotation(MapperSecurity.class);
                if (securityAnnotationDestination == null) {
                    MapperSecurity securityAnnotationSource = propertySource.getAnnotation(MapperSecurity.class);
                    if (securityAnnotationSource == null) {
                        initializeBeanOrEntity(source, destinationInstance, propertySource, propertyDestination, sourcePropertyInstance);
                    } else {
                        IdentityProvider identityManager = getIdentityManager();
                        if (hasRole(securityAnnotationSource, identityManager)) {
                            initializeBeanOrEntity(source, destinationInstance, propertySource, propertyDestination, sourcePropertyInstance);
                        }
                    }
                } else {
                    IdentityProvider identityManager = getIdentityManager();
                    if (hasRole(securityAnnotationDestination, identityManager)) {
                        initializeBeanOrEntity(source, destinationInstance, propertySource, propertyDestination, sourcePropertyInstance);
                    }
                }
            } else {
                Class<? extends Converter<?, ?>> converterClass = mapperSourceAnnotation.value();
                Converter converter = converterClass.getDeclaredConstructor().newInstance();
                Object object = converter.updater(sourcePropertyInstance);
                propertyDestination.accept(destinationInstance, object);
            }
        } else {
            Class<? extends Converter<?, ?>> converterClass = mapperDestinationAnnotation.value();
            Converter converter = converterClass.getDeclaredConstructor().newInstance();
            Object object = converter.factory(sourcePropertyInstance);
            propertyDestination.accept(destinationInstance, object);
        }
    }

    private static boolean hasRole(MapperSecurity securityAnnotationSource, IdentityProvider identityManager) {
        for (String role : securityAnnotationSource.rolesAllowed()) {
            if (! identityManager.hasRole(role)) {
                return false;
            }
        }
        return true;
    }

    private static IdentityProvider getIdentityManager() {
        return CDI.current().select(IdentityProvider.class).get();
    }

    private <S, D> void initializeBeanOrEntity(S source, D destinationInstance, BeanProperty<S, ?> propertySource, BeanProperty<D, Object> propertyDestination, Object sourcePropertyInstance) {
        if (propertySource.getType().isSubtypeOf(UUID.class) && propertyDestination.getType().isSubtypeOf(AbstractEntity.class)) {
            Object uuid = propertySource.apply(source);
            Object execute = newInstanceProvider.execute(uuid, propertyDestination.getType().getRawType());
            propertyDestination.accept(destinationInstance, execute);
        } else if (propertySource.getType().isSubtypeOf(AbstractEntity.class) && propertyDestination.getType().isSubtypeOf(UUID.class)) {
            AbstractEntity bean = (AbstractEntity) propertySource.apply(source);
            propertyDestination.accept(destinationInstance, bean.getId());
        } else {
            Object map = map(sourcePropertyInstance, propertyDestination.getType().getRawType());
            propertyDestination.accept(destinationInstance, map);
        }
    }

    private <D, S> void initializeList(D destinationInstance, BeanProperty<S, ?> propertySource, BeanProperty<D, Object> propertyDestination, List<?> list) {
        MapperSecurity securityAnnotation = propertySource.getAnnotation(MapperSecurity.class);
        if (securityAnnotation == null) {
            initializeListInternal(destinationInstance, propertyDestination, list);
        } else {
            IdentityProvider identityManager = getIdentityManager();
            if (hasRole(securityAnnotation, identityManager)) {
                initializeListInternal(destinationInstance, propertyDestination, list);
            }
        }
    }

    private <D> void initializeListInternal(D destinationInstance, BeanProperty<D, Object> propertyDestination, List<?> list) {
        Object destinationPropertyInstance = propertyDestination.apply(destinationInstance);
        List destinationList = (List) destinationPropertyInstance;
        destinationList.clear();
        list.forEach((item) -> {
            Object mappedInstance = map(item, propertyDestination.getType().resolveType(Collection.class.getTypeParameters()[0]).getRawType());
            destinationList.add(mappedInstance);
        });
    }

    private <D,S> void initializeMap(D destinationInstance, BeanProperty<S, ?> propertySource, BeanProperty<D, Object> propertyDestination, Map map) {
        MapperSecurity securityAnnotation = propertySource.getAnnotation(MapperSecurity.class);
        if (securityAnnotation == null) {
            initializeMapInternal(destinationInstance, propertyDestination, map);
        } else {
            IdentityProvider identityManager = getIdentityManager();
            if (hasRole(securityAnnotation, identityManager)) {
                initializeMapInternal(destinationInstance, propertyDestination, map);
            }
        }
    }

    private <D> void initializeMapInternal(D destinationInstance, BeanProperty<D, Object> propertyDestination, Map map) {
        Object destinationPropertyInstance = propertyDestination.apply(destinationInstance);
        Map destinationMap = (Map) destinationPropertyInstance;
        destinationMap.clear();
        map.forEach((key, value) -> {
            Object mappedKey = map(key, propertyDestination.getType().resolveType(Map.class.getTypeParameters()[0]).getRawType());
            Object mappedValue = map(value, propertyDestination.getType().resolveType(Map.class.getTypeParameters()[1]).getRawType());
            destinationMap.put(mappedKey, mappedValue);
        });
    }

    private <D,S> void initializeSet(D destinationInstance, BeanProperty<S, ?> propertySource, BeanProperty<D, Object> propertyDestination, Set<?> set) {
        MapperSecurity securityAnnotation = propertySource.getAnnotation(MapperSecurity.class);
        if (securityAnnotation == null) {
            initializeSetInternal(destinationInstance, propertyDestination, set);
        } else {
            IdentityProvider identityManager = getIdentityManager();
            if (hasRole(securityAnnotation, identityManager)) {
                initializeSetInternal(destinationInstance, propertyDestination, set);
            }
        }
    }

    private <D> void initializeSetInternal(D destinationInstance, BeanProperty<D, Object> propertyDestination, Set<?> set) {
        Object destinationPropertyInstance = propertyDestination.apply(destinationInstance);
        Set destinationSet = (Set) destinationPropertyInstance;
        destinationSet.clear();
        set.forEach((item) -> {
            Object mappedInstance = map(item, propertyDestination.getType().resolveType(Collection.class.getTypeParameters()[0]).getRawType());
            destinationSet.add(mappedInstance);
        });
    }

}
