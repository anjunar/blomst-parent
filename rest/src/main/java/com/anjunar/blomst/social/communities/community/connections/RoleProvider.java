package com.anjunar.blomst.social.communities.community.connections;

import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.Role_;
import com.anjunar.blomst.social.communities.CommunitiesConnection;
import com.anjunar.blomst.social.communities.CommunitiesConnection_;

import com.anjunar.introspector.bean.BeanProperty;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.UUID;

public class RoleProvider extends AbstractRestPredicateProvider<UUID, CommunitiesConnection> {
    @Override
    public Predicate build(UUID value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<CommunitiesConnection> root, CriteriaQuery<?> query, BeanProperty<?, ?> property) {
        if (value == null) {
            builder.conjunction();
        }
        return builder.equal(root.get(CommunitiesConnection_.ROLE).get(Role_.ID), value);
    }
}
