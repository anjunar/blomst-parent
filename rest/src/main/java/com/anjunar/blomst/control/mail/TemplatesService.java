package com.anjunar.blomst.control.mail;

import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.mail.Template;
import com.anjunar.common.security.IdentityProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

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
