package com.anjunar.blomst.social.pages.page.questions.question.answers;

import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.User_;
import com.anjunar.blomst.social.pages.page.Answer;
import com.anjunar.blomst.social.pages.page.Answer_;
import com.anjunar.common.security.IdentityManager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.UUID;

public class UserProvider extends AbstractRestPredicateProvider<UUID, Answer> {
    @Override
    public Predicate build(UUID value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<Answer> root, CriteriaQuery<?> query) {
        if (value == null) {
            return builder.conjunction();
        }
        return builder.equal(root.get(Answer_.owner).get(User_.id), value);
    }
}
