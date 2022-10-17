package com.anjunar.blomst.social.info.addresses;

import com.anjunar.blomst.control.users.Address;
import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.IdentityManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class AddressesService extends AbstractCriteriaSearchService<Address, AddressesSearch> {

    @Inject
    public AddressesService(EntityManager entityManager, IdentityManager identityManager) {
        super(entityManager, identityManager);
    }

    public AddressesService() {
        this(null, null);
    }
}
