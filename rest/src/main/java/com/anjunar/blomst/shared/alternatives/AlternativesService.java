package com.anjunar.blomst.shared.alternatives;

import com.anjunar.blomst.shared.Alternative;
import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.IdentityManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class AlternativesService extends AbstractCriteriaSearchService<Alternative, AlternativesSearch> {

    @Inject
    public AlternativesService(EntityManager entityManager, IdentityManager identityManager) {
        super(entityManager, identityManager);
    }

    public AlternativesService() {
        this(null, null);
    }

}
