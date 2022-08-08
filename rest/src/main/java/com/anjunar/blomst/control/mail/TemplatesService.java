package com.anjunar.blomst.control.mail;

import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.mail.Template;
import com.anjunar.common.security.IdentityProvider;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class TemplatesService extends AbstractCriteriaSearchService<Template, TemplatesSearch> {

    @Inject
    public TemplatesService(EntityManager entityManager, IdentityProvider identityProvider) {
        super(entityManager, identityProvider);
    }

    public TemplatesService() {
        this(null, null);
    }

}
