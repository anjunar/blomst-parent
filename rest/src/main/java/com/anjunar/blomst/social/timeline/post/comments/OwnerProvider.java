package com.anjunar.blomst.social.timeline.post.comments;

import com.anjunar.common.security.User_;
import com.anjunar.blomst.social.timeline.Comment;
import com.anjunar.blomst.social.timeline.Comment_;
import com.anjunar.common.rest.api.jaxrs.AbstractRestPredicateProvider;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.common.security.IdentityProvider;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class OwnerProvider extends AbstractRestPredicateProvider<UserSelect, Comment> {
    @Override
    public Predicate build(UserSelect value, IdentityProvider identityProvider, EntityManager entityManager, CriteriaBuilder builder, Root<Comment> root, CriteriaQuery<?> query) {
        if (value == null) {
            return builder.conjunction();
        }
        return builder.equal(root.get(Comment_.owner).get(User_.id), value);
    }
}
