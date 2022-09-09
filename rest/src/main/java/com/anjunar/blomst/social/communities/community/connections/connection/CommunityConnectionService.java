package com.anjunar.blomst.social.communities.community.connections.connection;

import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.Role;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class CommunityConnectionService {

    private final EntityManager entityManager;

    private final IdentityManager identityManager;

    @Inject
    public CommunityConnectionService(EntityManager entityManager, IdentityManager identityManager) {
        this.entityManager = entityManager;
        this.identityManager = identityManager;
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
