package com.anjunar.common.rest.api.jaxrs;

import com.anjunar.common.security.IdentityProvider;
import com.google.common.collect.Iterables;
import com.anjunar.introspector.bean.BeanIntrospector;
import com.anjunar.introspector.bean.BeanModel;
import com.anjunar.introspector.bean.BeanProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class RestSearch {

    private final static Logger log = LoggerFactory.getLogger(RestSearch.class);

    public static <X, Q extends AbstractRestSearch> List<Order> sort(
            Class<Q> queryClass,
            Q query,
            EntityManager entityManager,
            CriteriaBuilder builder,
            Root<X> root) {

        BeanModel<Q> beanModel = BeanIntrospector.create(queryClass);
        List<? extends BeanProperty<Q, ?>> properties = beanModel.getProperties();
        Optional<? extends BeanProperty<Q, ?>> restSortOptional = properties.stream()
                .filter((property) -> property.isAnnotationPresent(RestSort.class))
                .findFirst();
        if (restSortOptional.isPresent()) {
            BeanProperty<Q, ?> beanProperty = restSortOptional.get();
            RestSort annotation = beanProperty.getAnnotation(RestSort.class);
            Object value = beanProperty.apply(query);
            Class<? extends AbstractRestSortProvider> providerClass = annotation.value();
            try {
                AbstractRestSortProvider provider = providerClass.newInstance();
                return (List<Order>) provider.sort(value, entityManager, builder, root);
            } catch (InstantiationException | IllegalAccessException e) {
                log.error(e.getMessage(), e);
            }
        } else {
            throw new RuntimeException("No RestSort Annotation present on " + queryClass.getSimpleName());
        }
        return null;
    }

    public static <X, Q extends AbstractRestSearch> Predicate predicate(
            Class<Q> queryClass,
            Q search,
            IdentityProvider identityProvider,
            EntityManager entityManager,
            CriteriaBuilder builder,
            Root<X> root,
            CriteriaQuery<?> query) {

        BeanModel<Q> beanModel = BeanIntrospector.create(queryClass);
        List<? extends BeanProperty<Q, ?>> properties = beanModel.getProperties();
        Set<Predicate> predicates = properties.stream()
                .filter((property) -> property.isAnnotationPresent(RestPredicate.class) && Objects.nonNull(property.apply(search)))
                .map((beanProperty) -> {
                    RestPredicate annotation = beanProperty.getAnnotation(RestPredicate.class);
                    Object value = beanProperty.apply(search);
                    Class<? extends AbstractRestPredicateProvider> providerClass = annotation.value();
                    try {
                        AbstractRestPredicateProvider provider = providerClass.newInstance();
                        return provider.build(value, identityProvider, entityManager, builder, root, query);
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());

        return builder.and(Iterables.toArray(predicates, Predicate.class));
    }


}
