package com.anjunar.blomst.system.mail;

import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.mail.Template;
import com.anjunar.common.security.IdentityManager;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class TemplatesService extends AbstractCriteriaSearchService<Template, TemplatesSearch> {

    @Inject
    public TemplatesService(EntityManager entityManager, IdentityManager identityManager) {
        super(entityManager, identityManager);
    }

    public TemplatesService() {
        this(null, null);
    }

}
