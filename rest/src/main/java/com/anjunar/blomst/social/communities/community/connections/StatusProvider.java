package com.anjunar.blomst.social.communities.community.connections;

import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.blomst.social.communities.CommunitiesConnection;
import com.anjunar.blomst.social.communities.CommunitiesConnection_;
import com.anjunar.blomst.social.communities.Status;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class StatusProvider extends AbstractRestPredicateProvider<Status, CommunitiesConnection> {
    @Override
    public Predicate build(Status value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<CommunitiesConnection> root, CriteriaQuery<?> query) {
        if (value == null) {
            return builder.conjunction();
        }
        return builder.equal(root.get(CommunitiesConnection_.STATUS), value);
    }
}
