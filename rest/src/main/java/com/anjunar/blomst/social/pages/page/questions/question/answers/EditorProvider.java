package com.anjunar.blomst.social.pages.page.questions.question.answers;

import com.anjunar.blomst.social.pages.page.Answer;
import com.anjunar.blomst.social.pages.page.Answer_;
import com.anjunar.common.rest.api.jaxrs.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityProvider;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class EditorProvider extends AbstractRestPredicateProvider<String, Answer> {
    @Override
    public Predicate build(String value, IdentityProvider identityProvider, EntityManager entityManager, CriteriaBuilder builder, Root<Answer> root, CriteriaQuery<?> query) {
        if (value == null || value.equals("")) {
            return builder.conjunction();
        }
        return builder.gt(builder.function("contains", Integer.class, root.get(Answer_.text), builder.literal(value), builder.literal(1)), 0);
    }
}
