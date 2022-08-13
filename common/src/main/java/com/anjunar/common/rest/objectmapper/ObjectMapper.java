package com.anjunar.common.rest.objectmapper;

import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.introspector.bean.BeanIntrospector;
import com.anjunar.introspector.bean.BeanModel;
import com.anjunar.introspector.bean.BeanProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ObjectMapper {

    private static final Logger log = LoggerFactory.getLogger(ObjectMapper.class);

    private final NewInstanceProvider newInstanceProvider;

    private final IdProvider idProvider;

    public ObjectMapper(NewInstanceProvider newInstanceProvider, IdProvider idProvider) {
        this.newInstanceProvider = newInstanceProvider;
        this.idProvider = idProvider;
    }

    public ObjectMapper() {
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

    public ObjectMapper(NewInstanceProvider newInstanceProvider) {
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

    @SuppressWarnings("unchecked")
    public <S, D> D map(S source, Class<D> destinationClass) {
        if (destinationClass.isPrimitive() || source.getClass().equals(destinationClass)) {
            return (D) source;
        }

        try {
            D destinationInstance;
            if (source instanceof AbstractRestEntity) {
                Object id = idProvider.findID(source);
                if (id != null) {
                    destinationInstance = (D) newInstanceProvider.execute(id, destinationClass);
                    if (destinationInstance == null) {
                        destinationInstance = destinationClass.getDeclaredConstructor().newInstance();
                    }
                } else {
                    destinationInstance = destinationClass.getDeclaredConstructor().newInstance();
                }
            } else {
                destinationInstance = destinationClass.getDeclaredConstructor().newInstance();
            }

            BeanModel<S> beanModelSource = (BeanModel<S>) BeanIntrospector.create(source.getClass());
            BeanModel<D> beanModelDestination = BeanIntrospector.create(destinationClass);

            for (BeanProperty<S, ?> propertySource : beanModelSource.getProperties()) {
                BeanProperty<D, Object> propertyDestination = (BeanProperty<D, Object>) beanModelDestination.get(propertySource.getKey());

                if ((propertyDestination != null && ! propertyDestination.isReadOnly()) || propertyDestination != null && propertyDestination.getType().isSubtypeOf(Collection.class)) {
                    Object sourcePropertyInstance = propertySource.apply(source);

                    if (sourcePropertyInstance != null) {
                        switch (sourcePropertyInstance) {
                            case List<?> list -> {
                                Object destinationPropertyInstance = propertyDestination.apply(destinationInstance);
                                List destinationList = (List) destinationPropertyInstance;
                                destinationList.clear();
                                list.forEach((item) -> {
                                    Object mappedInstance = map(item, propertyDestination.getType().resolveType(Collection.class.getTypeParameters()[0]).getRawType());
                                    destinationList.add(mappedInstance);
                                });
                            }
                            case Set<?> set -> {
                                Object destinationPropertyInstance = propertyDestination.apply(destinationInstance);
                                Set destinationSet = (Set) destinationPropertyInstance;
                                destinationSet.clear();
                                set.forEach((item) -> {
                                    Object mappedInstance = map(item, propertyDestination.getType().resolveType(Collection.class.getTypeParameters()[0]).getRawType());
                                    destinationSet.add(mappedInstance);
                                });
                            }
                            case Map map -> {
                                Object destinationPropertyInstance = propertyDestination.apply(destinationInstance);
                                Map destinationMap = (Map) destinationPropertyInstance;
                                destinationMap.clear();
                                map.forEach((key, value) -> {
                                    Object mappedKey = map(key, propertyDestination.getType().resolveType(Map.class.getTypeParameters()[0]).getRawType());
                                    Object mappedValue = map(value, propertyDestination.getType().resolveType(Map.class.getTypeParameters()[1]).getRawType());
                                    destinationMap.put(mappedKey, mappedValue);
                                });
                            }
                            default -> {
                                Mapper mapperDestinationAnnotation = propertyDestination.getAnnotation(Mapper.class);
                                if (mapperDestinationAnnotation == null) {
                                    Mapper mapperSourceAnnotation = propertySource.getAnnotation(Mapper.class);
                                    if (mapperSourceAnnotation == null) {
                                        Object map = map(sourcePropertyInstance, propertyDestination.getType().getRawType());
                                        propertyDestination.accept(destinationInstance, map);
                                    } else {
                                        Class<? extends Converter<?, ?>> converterClass = mapperSourceAnnotation.value();
                                        Converter converter = converterClass.getDeclaredConstructor().newInstance();
                                        Object sourcePropertyID = idProvider.findID(sourcePropertyInstance);
                                        if (sourcePropertyID == null) {
                                            D destinationInstances = (D) propertyDestination.getType().getRawType().getDeclaredConstructor().newInstance();
                                            Object object = converter.updater(destinationInstances, sourcePropertyInstance);
                                            propertyDestination.accept(destinationInstance, object);
                                        } else {
                                            D destinationInstances = (D) newInstanceProvider.execute(sourcePropertyID, propertyDestination.getType().getRawType());
                                            Object object = converter.updater(destinationInstances, sourcePropertyInstance);
                                            propertyDestination.accept(destinationInstance, object);
                                        }
                                    }
                                } else {
                                    Class<? extends Converter<?, ?>> converterClass = mapperDestinationAnnotation.value();
                                    Converter converter = converterClass.getDeclaredConstructor().newInstance();
                                    Object object = converter.factory(sourcePropertyInstance);
                                    propertyDestination.accept(destinationInstance, object);
                                }
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

}
