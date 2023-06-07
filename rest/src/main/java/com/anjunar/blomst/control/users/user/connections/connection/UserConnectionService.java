package com.anjunar.blomst.control.users.user.connections.connection;

import com.anjunar.common.security.UserConnection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.UUID;

@ApplicationScoped
public class UserConnectionService {

    private final EntityManager entityManager;

    @Inject
    public UserConnectionService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public UserConnectionService() {
        this(null);
    }

    public UserConnection accepted(UUID from, UUID to) {
        try {
            return entityManager.createQuery("select c from UserConnection c where c.from.id = :from and c.to.id = :to", UserConnection.class)
                    .setParameter("from", from)
                    .setParameter("to", to)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
