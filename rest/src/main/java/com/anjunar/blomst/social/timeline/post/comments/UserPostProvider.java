package com.anjunar.blomst.social.timeline.post.comments;

import com.anjunar.blomst.social.timeline.AbstractPost_;
import com.anjunar.blomst.social.timeline.Comment;
import com.anjunar.blomst.social.timeline.Comment_;
import com.anjunar.common.rest.api.jaxrs.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityProvider;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.UUID;

public class UserPostProvider extends AbstractRestPredicateProvider<UUID, Comment> {
    @Override
    public Predicate build(UUID value, IdentityProvider identityProvider, EntityManager entityManager, CriteriaBuilder builder, Root<Comment> root, CriteriaQuery<?> query) {
        if (value == null) {
            return builder.conjunction();
        }

        return builder.and(builder.equal(root.get(Comment_.post).get(AbstractPost_.id), value), builder.isNull(root.get(Comment_.PARENT).get(Comment_.ID)));
    }
}
