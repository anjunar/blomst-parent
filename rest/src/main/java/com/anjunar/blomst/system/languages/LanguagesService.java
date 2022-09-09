package com.anjunar.blomst.system.languages;

import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.i18n.Language;
import com.anjunar.common.security.IdentityManager;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

public class LanguagesService extends AbstractCriteriaSearchService<Language, LanguagesSearch> {

    @Inject
    public LanguagesService(EntityManager entityManager, IdentityManager identityManager) {
        super(entityManager, identityManager);
    }

    public LanguagesService() {
        this(null, null);
    }

}
