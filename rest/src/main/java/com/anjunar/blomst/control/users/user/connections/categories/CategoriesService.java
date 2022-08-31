package com.anjunar.blomst.control.users.user.connections.categories;

import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.Category;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class CategoriesService extends AbstractCriteriaSearchService<Category, CategoriesSearch> {

    @Inject
    public CategoriesService(EntityManager entityManager, IdentityProvider identityProvider) {
        super(entityManager, identityProvider);
    }

    public CategoriesService() {
        this(null, null);
    }

}
