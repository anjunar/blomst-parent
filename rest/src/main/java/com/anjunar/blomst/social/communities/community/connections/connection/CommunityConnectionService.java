package com.anjunar.blomst.social.communities.community.connections.connection;

import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.Role;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class CommunityConnectionService {

    private final EntityManager entityManager;

    private final IdentityProvider identityProvider;

    @Inject
    public CommunityConnectionService(EntityManager entityManager, IdentityProvider identityProvider) {
        this.entityManager = entityManager;
        this.identityProvider = identityProvider;
    }

    public CommunityConnectionService() {
        this(null, null);
    }

    public Role findUserRole() {
        return entityManager.createQuery("select r from Role r where r.name = :name", Role.class)
                .setParameter("name", "User")
                .getSingleResult();
    }
}
