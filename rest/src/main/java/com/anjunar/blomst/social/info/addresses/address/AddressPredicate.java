package com.anjunar.blomst.social.info.addresses.address;

import com.anjunar.blomst.control.users.Address;
import com.anjunar.common.rest.MethodPredicateHandler;
import com.anjunar.common.security.IdentityManager;
import jakarta.persistence.EntityManager;

import java.util.UUID;

public class AddressPredicate implements MethodPredicateHandler {

    private final IdentityManager identityManager;

    private final EntityManager entityManager;

    public AddressPredicate(IdentityManager identityManager, EntityManager entityManager) {
        this.identityManager = identityManager;
        this.entityManager = entityManager;
    }

    @Override
    public boolean apply(UUID id) {
        if (identityManager.hasRole("Administrator")) {
            return true;
        }
        Address question = entityManager.find(Address.class, id);
        return question.getOwner().getId().equals(identityManager.getUser().getId());
    }
}
