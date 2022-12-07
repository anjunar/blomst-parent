package com.anjunar.blomst.social.timeline.post.comments.comment.likes;

import com.anjunar.blomst.social.timeline.Comment;
import com.anjunar.blomst.social.timeline.Comment_;
import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.User;
import com.anjunar.introspector.bean.BeanProperty;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

import java.util.Objects;
import java.util.UUID;

public class CommentProvider extends AbstractRestPredicateProvider<UUID, User> {
    @Override
    public Predicate build(UUID value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<User> root, CriteriaQuery<?> query, BeanProperty<?, ?> property) {
        if (Objects.nonNull(value)) {
            Subquery<User> subQuery = query.subquery(User.class);
            Root<Comment> subFrom = subQuery.from(Comment.class);
            subQuery.select(subFrom.join(Comment_.likes)).where(builder.equal(subFrom.get(Comment_.id), value));
            return root.in(subQuery);
        }
        return builder.conjunction();
    }
}
