package com.anjunar.blomst.control.users.user.connections;

import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.control.users.UserConnection;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

public class UserConnectionsService extends AbstractCriteriaSearchService<UserConnection, UserConnectionsSearch> {

    @Inject
    public UserConnectionsService(EntityManager entityManager, IdentityProvider identityProvider) {
        super(entityManager, identityProvider);
    }

    public UserConnectionsService() {
        this(null, null);
    }

    public List<UserConnection> accepted(UUID from) {
        return entityManager.createQuery("select c from UserConnection c where c.to.id = :user", UserConnection.class)
                .setParameter("user", from)
                .getResultList();
    }

}
