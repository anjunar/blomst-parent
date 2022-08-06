package com.anjunar.blomst.social.communities.community;

import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.Role;
import com.anjunar.blomst.social.communities.CommunitiesConnection;
import com.anjunar.blomst.social.communities.Community;
import com.anjunar.blomst.social.communities.Status;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.UUID;

@ApplicationScoped
public class CommunityService {

    private final EntityManager entityManager;

    private final IdentityProvider identityProvider;

    @Inject
    public CommunityService(EntityManager entityManager, IdentityProvider identityProvider) {
        this.entityManager = entityManager;
        this.identityProvider = identityProvider;
    }

    public CommunityService() {
        this(null, null);
    }

    public void addAdministrator(Community entity) {
        CommunitiesConnection connection = new CommunitiesConnection();
        connection.setFrom(identityProvider.getUser());
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
            CommunitiesConnection connection = entityManager.createQuery("select c from CommunitiesConnection c where c.from = :from and c.to.id = :to", CommunitiesConnection.class)
                    .setParameter("from", identityProvider.getUser())
                    .setParameter("to", community)
                    .getSingleResult();

            return connection.getRole().getName().equals(name);
        } catch (NoResultException e) {
            return false;
        }
    }

    public CommunitiesConnection findConnection(UUID from, UUID to) {
        return entityManager.createQuery("select c from CommunitiesConnection c where c.from.id = :from and c.to.id = :to", CommunitiesConnection.class)
                .setParameter("from", from)
                .setParameter("to", to)
                .getSingleResult();
    }
}
