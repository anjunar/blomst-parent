package com.anjunar.blomst.social.pages.page.questions.question.answers;

import com.anjunar.common.security.User_;
import com.anjunar.blomst.social.pages.page.Answer;
import com.anjunar.blomst.social.pages.page.Answer_;
import com.anjunar.common.rest.api.jaxrs.AbstractRestPredicateProvider;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.common.security.IdentityProvider;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserProvider extends AbstractRestPredicateProvider<UserSelect, Answer> {
    @Override
    public Predicate build(UserSelect value, IdentityProvider identityProvider, EntityManager entityManager, CriteriaBuilder builder, Root<Answer> root, CriteriaQuery<?> query) {
        if (value == null) {
            return builder.conjunction();
        }
        return builder.equal(root.get(Answer_.owner).get(User_.id), value.getId());
    }
}
