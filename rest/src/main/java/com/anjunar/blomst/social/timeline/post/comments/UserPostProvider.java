package com.anjunar.blomst.social.timeline.post.comments;

import com.anjunar.blomst.social.timeline.AbstractPost_;
import com.anjunar.blomst.social.timeline.Comment;
import com.anjunar.blomst.social.timeline.Comment_;
import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityManager;

import com.anjunar.introspector.bean.BeanProperty;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.UUID;

public class UserPostProvider extends AbstractRestPredicateProvider<UUID, Comment> {
    @Override
    public Predicate build(UUID value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<Comment> root, CriteriaQuery<?> query, BeanProperty<?, ?> property) {
        if (value == null) {
            return builder.conjunction();
        }

        return builder.and(builder.equal(root.get(Comment_.post).get(AbstractPost_.id), value), builder.isNull(root.get(Comment_.PARENT).get(Comment_.ID)));
    }
}
