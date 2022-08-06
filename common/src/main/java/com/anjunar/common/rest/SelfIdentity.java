package com.anjunar.common.rest;

import com.anjunar.common.security.IdentityProvider;

import javax.persistence.EntityManager;
import java.util.UUID;

public class SelfIdentity {

        private final IdentityProvider identityProvider;

        private final EntityManager entityManager;

    public SelfIdentity(IdentityProvider identityProvider, EntityManager entityManager) {
        this.identityProvider = identityProvider;
        this.entityManager = entityManager;
    }

    public boolean apply(UUID id) {
        if (! identityProvider.hasRole("Administrator")) {
            return id.equals(identityProvider.getUser().getId());
        }
        return true;
    }
}
