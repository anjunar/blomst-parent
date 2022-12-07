package com.anjunar.blomst.social.pages.page.likes;

import com.anjunar.blomst.social.pages.Page;
import com.anjunar.blomst.social.pages.Page_;
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

public class PageProvider extends AbstractRestPredicateProvider<UUID, User> {
    @Override
    public Predicate build(UUID value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<User> root, CriteriaQuery<?> query, BeanProperty<?, ?> property) {
        if (Objects.nonNull(value)) {
            Subquery<User> subQuery = query.subquery(User.class);
            Root<Page> subFrom = subQuery.from(Page.class);
            subQuery.select(subFrom.join(Page_.likes)).where(builder.equal(subFrom.get(Page_.id), value));
            return root.in(subQuery);
        }
        return builder.conjunction();
    }
}
