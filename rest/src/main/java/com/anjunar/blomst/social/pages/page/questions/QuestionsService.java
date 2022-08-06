package com.anjunar.blomst.social.pages.page.questions;

import com.anjunar.blomst.social.pages.page.Question;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.ddd.AbstractCriteriaSearchService;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class QuestionsService extends AbstractCriteriaSearchService<Question, QuestionsSearch> {

    @Inject
    public QuestionsService(EntityManager entityManager, IdentityProvider identityProvider) {
        super(entityManager, identityProvider);
    }

    public QuestionsService() {
        this(null, null);
    }

}
