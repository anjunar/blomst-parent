package com.anjunar.blomst.social.communities.community.connections;

import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.blomst.social.communities.CommunityConnection;
import com.anjunar.blomst.social.communities.CommunityConnection_;
import com.anjunar.blomst.social.communities.Community_;

import com.anjunar.introspector.bean.BeanProperty;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.UUID;

public class ToProvider extends AbstractRestPredicateProvider<UUID, CommunityConnection> {
    @Override
    public Predicate build(UUID value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<CommunityConnection> root, CriteriaQuery<?> query, BeanProperty<?, ?> property) {
        if (value == null) {
            return builder.conjunction();
        }
        return builder.equal(root.get(CommunityConnection_.TO).get(Community_.ID), value);
    }
}
