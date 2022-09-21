package com.anjunar.common.rest.search.provider;

import com.anjunar.introspector.bean.BeanProperty;
import com.github.pemistahl.lingua.api.Language;
import com.github.pemistahl.lingua.api.LanguageDetector;
import com.github.pemistahl.lingua.api.LanguageDetectorBuilder;
import com.google.common.base.Strings;
import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityManager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class GenericTextProvider<E> extends AbstractRestPredicateProvider<String, E> {

    private final static LanguageDetector detector = LanguageDetectorBuilder
            .fromAllLanguages()
            .build();

    @Override
    public Predicate build(String value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<E> root, CriteriaQuery<?> query, BeanProperty<?, ?> property) {
        if (Strings.isNullOrEmpty(value)) {
            return builder.conjunction();
        }

        Language language = detector.detectLanguageOf(value);
        String languageString = language.name().toLowerCase();

        return builder.equal(builder.function("distance", Boolean.class, root.get(property.getKey()).get("text"), builder.literal(languageString), builder.literal(value)), true);
    }
}
