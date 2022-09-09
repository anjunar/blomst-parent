package com.anjunar.blomst.social.pages.page.questions;

import com.anjunar.blomst.social.pages.Editor_;
import com.anjunar.blomst.social.pages.page.Question;
import com.anjunar.blomst.social.pages.page.Question_;
import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityManager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class TextProvider extends AbstractRestPredicateProvider<String, Question> {
    @Override
    public Predicate build(String value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<Question> root, CriteriaQuery<?> query) {
        if (value == null || value.equals("")) {
            return builder.conjunction();
        }
        return builder.gt(builder.function("contains", Integer.class, root.get(Question_.editor).get(Editor_.text), builder.literal("about(" + value + ")")), 0);
    }
}
