package com.anjunar.blomst.social.timeline.post.likes;

import com.anjunar.blomst.shared.Likeable_;
import com.anjunar.blomst.social.timeline.AbstractPost;
import com.anjunar.blomst.social.timeline.AbstractPost_;
import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.User;
import com.anjunar.introspector.bean.BeanProperty;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

import java.util.Objects;
import java.util.UUID;

public class PostProvider extends AbstractRestPredicateProvider<UUID, User> {
    @Override
    public Predicate build(UUID value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<User> root, CriteriaQuery<?> query, BeanProperty<?, ?> property) {
        if (Objects.nonNull(value)) {
            Subquery<User> subQuery = query.subquery(User.class);
            Root<AbstractPost> subFrom = subQuery.from(AbstractPost.class);
            subQuery.select(subFrom.join(Likeable_.likes)).where(builder.equal(subFrom.get(Likeable_.id), value));
            return root.in(subQuery);
        }
        return builder.conjunction();
    }
}
