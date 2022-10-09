package com.anjunar.common.rest.search.provider;

import com.anjunar.common.i18n.Detector;
import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.introspector.bean.BeanProperty;
import com.google.common.base.Strings;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class GenericTextProvider<E> extends AbstractRestPredicateProvider<String, E> {

    @Override
    public Predicate build(String value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<E> root, CriteriaQuery<?> query, BeanProperty<?, ?> property) {
        if (Strings.isNullOrEmpty(value)) {
            return builder.conjunction();
        }

        String language = Detector.detectLanguageOf(value);
        String languageString = language.toLowerCase();

        return builder.equal(builder.function("distance", Boolean.class, root.get(property.getKey()).get("text"), builder.literal(languageString), builder.literal(value)), true);
    }
}
