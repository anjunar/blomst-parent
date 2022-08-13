package com.anjunar.blomst.social.timeline;

import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.Identity_;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.Set;
import java.util.UUID;

public class SourceProvider extends AbstractRestPredicateProvider<Set<UUID>, AbstractPost> {
    @Override
    public Predicate build(Set<UUID> value, IdentityProvider identityProvider, EntityManager entityManager, CriteriaBuilder builder, Root<AbstractPost> root, CriteriaQuery<?> query) {
        if (value == null || value.size() == 0) {
            return builder.conjunction();
        }
        return root.get(AbstractPost_.SOURCE).get(Identity_.ID).in(value);
    }
}
