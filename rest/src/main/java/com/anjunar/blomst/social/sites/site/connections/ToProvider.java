package com.anjunar.blomst.social.sites.site.connections;

import com.anjunar.common.rest.api.jaxrs.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.social.sites.SiteConnection;
import com.anjunar.blomst.social.sites.SiteConnection_;
import com.anjunar.blomst.social.sites.Site_;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.UUID;

public class ToProvider extends AbstractRestPredicateProvider<UUID, SiteConnection> {
    @Override
    public Predicate build(UUID value, IdentityProvider identityProvider, EntityManager entityManager, CriteriaBuilder builder, Root<SiteConnection> root, CriteriaQuery<?> query) {
        if (value == null) {
            return builder.conjunction();
        }
        return builder.equal(root.get(SiteConnection_.TO).get(Site_.ID), value);
    }
}
