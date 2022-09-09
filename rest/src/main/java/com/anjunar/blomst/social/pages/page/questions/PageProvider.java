package com.anjunar.blomst.social.pages.page.questions;

import com.anjunar.blomst.social.pages.Page_;
import com.anjunar.blomst.social.pages.page.Question;
import com.anjunar.blomst.social.pages.page.Question_;
import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityManager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.UUID;

public class PageProvider extends AbstractRestPredicateProvider<UUID, Question> {
    @Override
    public Predicate build(UUID value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<Question> root, CriteriaQuery<?> query) {
        if (value == null) {
            return builder.conjunction();
        }
        return builder.equal(root.get(Question_.page).get(Page_.id), value);
    }
}
