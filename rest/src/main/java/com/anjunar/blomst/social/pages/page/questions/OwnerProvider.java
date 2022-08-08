package com.anjunar.blomst.social.pages.page.questions;

import com.anjunar.common.security.User_;
import com.anjunar.blomst.social.pages.page.Question;
import com.anjunar.blomst.social.pages.page.Question_;
import com.anjunar.common.rest.api.jaxrs.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.shared.users.user.UserSelect;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class OwnerProvider extends AbstractRestPredicateProvider<UserSelect, Question> {
    @Override
    public Predicate build(UserSelect value, IdentityProvider identityProvider, EntityManager entityManager, CriteriaBuilder builder, Root<Question> root, CriteriaQuery<?> query) {
        if (value == null) {
            return builder.conjunction();
        }
        return builder.equal(root.get(Question_.owner).get(User_.id), value.getId());
    }
}
