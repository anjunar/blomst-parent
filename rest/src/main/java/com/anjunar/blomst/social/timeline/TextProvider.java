package com.anjunar.blomst.social.timeline;

import com.anjunar.blomst.social.pages.Editor_;
import com.google.common.base.Strings;
import com.anjunar.blomst.social.pages.page.Question;
import com.anjunar.blomst.social.pages.page.Question_;
import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityProvider;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class TextProvider extends AbstractRestPredicateProvider<String, Question> {
    @Override
    public Predicate build(String value, IdentityProvider identityProvider, EntityManager entityManager, CriteriaBuilder builder, Root<Question> root, CriteriaQuery<?> query) {
        if (Strings.isNullOrEmpty(value)) {
            return builder.conjunction();
        }
        return builder.greaterThan(builder.function("contains", Integer.class, root.get(Question_.editor).get(Editor_.text), builder.literal("about(" + value + ")")), 0);
    }
}
