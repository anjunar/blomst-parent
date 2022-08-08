package com.anjunar.blomst.social.communities.community.connections;

import com.anjunar.common.rest.api.jaxrs.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.User_;
import com.anjunar.blomst.social.communities.CommunitiesConnection;
import com.anjunar.blomst.social.communities.CommunitiesConnection_;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.UUID;

public class FromProvider extends AbstractRestPredicateProvider<UUID, CommunitiesConnection> {
    @Override
    public Predicate build(UUID value, IdentityProvider identityProvider, EntityManager entityManager, CriteriaBuilder builder, Root<CommunitiesConnection> root, CriteriaQuery<?> query) {
        if (value == null) {
            return builder.conjunction();
        }
        return builder.equal(root.get(CommunitiesConnection_.FROM).get(User_.ID), value);
    }
}
