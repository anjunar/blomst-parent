package com.anjunar.common.rest;

import com.anjunar.common.security.IdentityManager;
import jakarta.persistence.EntityManager;

import java.util.UUID;

public class MyOwnIdentity implements MethodPredicateHandler {

    private final IdentityManager identityManager;

    private final EntityManager entityManager;

    public MyOwnIdentity(IdentityManager identityManager, EntityManager entityManager) {
        this.identityManager = identityManager;
        this.entityManager = entityManager;
    }

    public boolean apply(UUID id) {
        if (!identityManager.hasRole("Administrator")) {
            return id.equals(identityManager.getUser().getId());
        }
        return true;
    }
}
