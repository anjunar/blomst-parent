package com.anjunar.blomst.control.users.user.connections.categories;

import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.control.users.Category;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

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
