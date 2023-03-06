package com.anjunar.blomst.social.timeline;

import com.anjunar.blomst.social.pages.Editor_;
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

public class TextProvider extends AbstractRestPredicateProvider<String, AbstractPost> {

    @Override
    public Predicate build(String value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<AbstractPost> root, CriteriaQuery<?> query, BeanProperty<?, ?> property) {
        if (Strings.isNullOrEmpty(value)) {
            return builder.conjunction();
        }

        String language = Detector.detectLanguageOf(value);

        return builder.equal(builder.function("distance", Boolean.class, root.get(AbstractPost_.editor).get(Editor_.text), builder.literal(language.toLowerCase()), builder.literal(value)), true);
    }
}
