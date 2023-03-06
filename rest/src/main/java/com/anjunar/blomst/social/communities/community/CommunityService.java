package com.anjunar.blomst.social.communities.community;

import com.anjunar.blomst.social.communities.CommunityConnection;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.Role;
import com.anjunar.blomst.social.communities.Community;
import com.anjunar.blomst.social.communities.Status;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.UUID;

@ApplicationScoped
public class CommunityService {

    private final EntityManager entityManager;

    private final IdentityManager identityManager;

    @Inject
    public CommunityService(EntityManager entityManager, IdentityManager identityManager) {
        this.entityManager = entityManager;
        this.identityManager = identityManager;
    }

    public CommunityService() {
        this(null, null);
    }

    public void addAdministrator(Community entity) {
        CommunityConnection connection = new CommunityConnection();
        connection.setFrom(identityManager.getUser());
        connection.setTo(entity);
        connection.setStatus(Status.OK);

        Role administrator = entityManager.createQuery("select r from Role r where r.name = :name", Role.class)
                .setParameter("name", "Administrator")
                .getSingleResult();

        connection.setRole(administrator);
        entityManager.persist(connection);
    }

    public boolean hasRole(String name, UUID community) {
        try {
            CommunityConnection connection = entityManager.createQuery("select c from CommunitiesConnection c where c.from = :from and c.to.id = :to", CommunityConnection.class)
                    .setParameter("from", identityManager.getUser())
                    .setParameter("to", community)
                    .getSingleResult();

            return connection.getRole().getName().equals(name);
        } catch (NoResultException e) {
            return false;
        }
    }

    public CommunityConnection findConnection(UUID from, UUID to) {
        return entityManager.createQuery("select c from CommunitiesConnection c where c.from.id = :from and c.to.id = :to", CommunityConnection.class)
                .setParameter("from", from)
                .setParameter("to", to)
                .getSingleResult();
    }
}
