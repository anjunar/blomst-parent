package com.anjunar.common.rest.schemamapper;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.AbstractSchemaEntity;
import com.anjunar.common.rest.schema.CategoryType;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.security.*;
import com.anjunar.introspector.bean.BeanIntrospector;
import com.anjunar.introspector.bean.BeanModel;
import com.anjunar.introspector.bean.BeanProperty;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@SuppressWarnings({"unchecked", "UnstableApiUsage", "rawtypes"})
public class ResourceMapper {

    private static final Logger log = LoggerFactory.getLogger(ResourceMapper.class);

    private final NewInstanceProvider newInstanceProvider;

    private final IdProvider idProvider;

    private final SchemaProvider schemaProvider;

    public ResourceMapper(NewInstanceProvider newInstanceProvider, IdProvider idProvider, SchemaProvider schemaProvider) {
        this.newInstanceProvider = newInstanceProvider;
        this.idProvider = idProvider;
        this.schemaProvider = schemaProvider;
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
                source -> null,
                (entity, aClass, property) -> {
                    if (entity.getOwner().equals(getIdentityManager().getUser())) {
                        return true;
                    }

                    try {
                        EntitySchema schemaItem = getEntityManager().createQuery("select s from EntitySchema s where s.property = :property and s.entity = :entity and s.owner = :owner", EntitySchema.class)
                                .setParameter("property", property)
                                .setParameter("entity", aClass)
                                .setParameter("owner", entity.getOwner())
                                .getSingleResult();

                        UserConnection userConnection = getEntityManager().createQuery("select c from UserConnection c where c.to = :to and c.from = :from", UserConnection.class)
                                .setParameter("to", getIdentityManager().getUser())
                                .setParameter("from", entity.getOwner())
                                .getSingleResult();

                        Set<Category> visibility = schemaItem.getVisibility();

                        return visibility.contains(userConnection.getCategory());
                    } catch (NoResultException e) {
                        return false;
                    }
                });
    }

    public ResourceMapper(NewInstanceProvider newInstanceProvider) {
        this(
                newInstanceProvider,
                source -> {
                    if (source instanceof AbstractRestEntity) {
                        return ((AbstractRestEntity) source).getId();
                    }
                    return null;
                },
                (entity, aClass, property) -> {
                    if (entity.getOwner().equals(getIdentityManager().getUser())) {
                        return true;
                    }

                    try {
                        EntitySchema schemaItem = getEntityManager().createQuery("select s from EntitySchema s where s.property = :property and s.entity = :entity and s.owner = :owner", EntitySchema.class)
                                .setParameter("property", property)
                                .setParameter("entity", aClass)
                                .setParameter("owner", entity.getOwner())
                                .getSingleResult();

                        UserConnection userConnection = getEntityManager().createQuery("select c from UserConnection c where c.to = :to and c.from = :from", UserConnection.class)
                                .setParameter("to", getIdentityManager().getUser())
                                .setParameter("from", entity.getOwner())
                                .getSingleResult();

                        Set<Category> visibility = schemaItem.getVisibility();

                        return visibility.contains(userConnection.getCategory());
                    } catch (NoResultException e) {
                        return false;
                    }
                });
    }

    public <S, D> D map(S source, Class<D> destinationClass) {
        if (destinationClass.isPrimitive() || source.getClass().equals(destinationClass)) {
            return (D) source;
        }

        if (source instanceof AbstractRestEntity) {
            JsonObject schema = ((AbstractRestEntity) source).getSchema();

            for (Map.Entry<String, JsonNode> entry : schema.getProperties().entrySet()) {
                Set<CategoryType> categories = entry.getValue().getCategories();

                try {
                    EntityManager entityManager = getEntityManager();
                    EntitySchema entitySchema = entityManager.createQuery("select s from EntitySchema s where s.owner = :owner and s.entity = :entity and s.property = :property", EntitySchema.class)
                            .setParameter("owner", getIdentityManager().getUser())
                            .setParameter("entity", destinationClass)
                            .setParameter("property", entry.getKey())
                            .getSingleResult();

                    entitySchema.getVisibility().clear();

                    for (CategoryType category : categories) {
                        Category categoryEntity = entityManager.find(Category.class, category.getId());
                        entitySchema.getVisibility().add(categoryEntity);
                    }
                } catch (NoResultException e) {
                    log.info(e.getLocalizedMessage());
                }
            }
        }

        try {
            boolean isNew = false;
            D destinationInstance = newInstance(source, destinationClass);

            if (destinationInstance == null) {
                destinationInstance = destinationClass.getDeclaredConstructor().newInstance();
                isNew = true;
            }

            if (destinationInstance instanceof AbstractRestEntity) {
                JsonObject schema = ((AbstractRestEntity) destinationInstance).getSchema();

                for (Map.Entry<String, JsonNode> entry : schema.getProperties().entrySet()) {
                    try {
                        Set<CategoryType> categories = entry.getValue().getCategories();
                        EntityManager entityManager = getEntityManager();
                        EntitySchema entitySchema = entityManager.createQuery("select s from EntitySchema s where s.owner = :owner and s.entity = :entity and s.property = :property", EntitySchema.class)
                                .setParameter("owner", getIdentityManager().getUser())
                                .setParameter("entity", source.getClass())
                                .setParameter("property", entry.getKey())
                                .getSingleResult();

                        for (Category category : entitySchema.getVisibility()) {
                            CategoryType categoryType = map(category, CategoryType.class);
                            categories.add(categoryType);
                        }
                    } catch (NoResultException e) {
                        log.info(e.getLocalizedMessage());
                    }
                }


            }

            BeanModel<S> beanModelSource = (BeanModel<S>) BeanIntrospector.create(source.getClass());
            BeanModel<D> beanModelDestination = BeanIntrospector.create(destinationClass);

            for (BeanProperty<S, ?> propertySource : beanModelSource.getProperties()) {
                Boolean dirty = isDirty(source, propertySource);

                if (isNew || dirty) {
                    BeanProperty<D, Object> propertyDestination = (BeanProperty<D, Object>) beanModelDestination.get(propertySource.getKey());

                    if ((propertyDestination != null && !propertyDestination.isReadOnly()) || propertyDestination != null && propertyDestination.getType().isSubtypeOf(Collection.class)) {
                        Object sourcePropertyInstance = propertySource.apply(source);

                        if (sourcePropertyInstance != null) {
                            switch (sourcePropertyInstance) {
                                case List<?> list ->
                                        initializeList(source, destinationInstance, propertySource, propertyDestination, list);
                                case Set<?> set ->
                                        initializeSet(source, destinationInstance, propertySource, propertyDestination, set);
                                case Map map ->
                                        initializeMap(source, destinationInstance, propertySource, propertyDestination, map);
                                default ->
                                        initializeBean(source, destinationInstance, propertySource, propertyDestination, sourcePropertyInstance);
                            }
                        }
                    }
                }
            }
            return destinationInstance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
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
                        MapperSchema schemaAnnotation = propertySource.getAnnotation(MapperSchema.class);
                        if (schemaAnnotation == null) {
                            initializeBeanOrEntity(source, destinationInstance, propertySource, propertyDestination, sourcePropertyInstance);
                        } else {
                            boolean execute = schemaProvider.execute((OwnerProvider) source, source.getClass(), propertySource.getKey());
                            if (execute) {
                                initializeBeanOrEntity(source, destinationInstance, propertySource, propertyDestination, sourcePropertyInstance);
                            }
                        }
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
            if (!identityManager.hasRole(role)) {
                return false;
            }
        }
        return true;
    }

    private static IdentityProvider getIdentityManager() {
        return CDI.current().select(IdentityProvider.class).get();
    }

    private static EntityManager getEntityManager() {
        return CDI.current().select(EntityManager.class).get();
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

    private <D, S> void initializeList(S source, D destinationInstance, BeanProperty<S, ?> propertySource, BeanProperty<D, Object> propertyDestination, List<?> list) {
        MapperSecurity securityAnnotation = propertySource.getAnnotation(MapperSecurity.class);
        if (securityAnnotation == null) {
            MapperSchema schemaAnnotation = propertySource.getAnnotation(MapperSchema.class);
            if (schemaAnnotation == null) {
                initializeListInternal(destinationInstance, propertyDestination, list);
            } else {
                boolean execute = schemaProvider.execute((OwnerProvider) source, source.getClass(), propertySource.getKey());
                if (execute) {
                    initializeListInternal(destinationInstance, propertyDestination, list);
                }
            }
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

    private <D, S> void initializeMap(S source, D destinationInstance, BeanProperty<S, ?> propertySource, BeanProperty<D, Object> propertyDestination, Map map) {
        MapperSecurity securityAnnotation = propertySource.getAnnotation(MapperSecurity.class);
        if (securityAnnotation == null) {
            MapperSchema schemaAnnotation = propertySource.getAnnotation(MapperSchema.class);
            if (schemaAnnotation == null) {
                initializeMapInternal(destinationInstance, propertyDestination, map);
            } else {
                boolean execute = schemaProvider.execute((OwnerProvider) source, source.getClass(), propertySource.getKey());
                if (execute) {
                    initializeMapInternal(destinationInstance, propertyDestination, map);
                }
            }
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

    private <D, S> void initializeSet(S source, D destinationInstance, BeanProperty<S, ?> propertySource, BeanProperty<D, Object> propertyDestination, Set<?> set) {
        MapperSecurity securityAnnotation = propertySource.getAnnotation(MapperSecurity.class);
        if (securityAnnotation == null) {
            MapperSchema schemaAnnotation = propertySource.getAnnotation(MapperSchema.class);
            if (schemaAnnotation == null) {
                initializeSetInternal(destinationInstance, propertyDestination, set);
            } else {
                boolean execute = schemaProvider.execute((OwnerProvider) source, source.getClass(), propertySource.getKey());
                if (execute) {
                    initializeSetInternal(destinationInstance, propertyDestination, set);
                }
            }
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
