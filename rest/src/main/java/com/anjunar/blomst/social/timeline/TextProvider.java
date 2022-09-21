package com.anjunar.blomst.social.timeline;

import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.introspector.bean.BeanProperty;
import com.github.pemistahl.lingua.api.Language;
import com.github.pemistahl.lingua.api.LanguageDetector;
import com.github.pemistahl.lingua.api.LanguageDetectorBuilder;
import com.google.common.base.Strings;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class TextProvider extends AbstractRestPredicateProvider<String, AbstractPost> {

    private final static LanguageDetector detector = LanguageDetectorBuilder
            .fromAllLanguages()
            .build();

    @Override
    public Predicate build(String value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<AbstractPost> root, CriteriaQuery<?> query, BeanProperty<?, ?> property) {
        if (Strings.isNullOrEmpty(value)) {
            return builder.conjunction();
        }

        Language language = detector.detectLanguageOf(value);

        return builder.equal(builder.function("distance", Boolean.class, root.get(AbstractPost_.text), builder.literal(language.name().toLowerCase()), builder.literal(value)), true);
    }
}
