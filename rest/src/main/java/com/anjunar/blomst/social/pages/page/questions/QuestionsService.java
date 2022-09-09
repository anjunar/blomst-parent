package com.anjunar.blomst.social.pages.page.questions;

import com.anjunar.blomst.social.pages.page.Question;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.ddd.AbstractCriteriaSearchService;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

public class QuestionsService extends AbstractCriteriaSearchService<Question, QuestionsSearch> {

    @Inject
    public QuestionsService(EntityManager entityManager, IdentityManager identityManager) {
        super(entityManager, identityManager);
    }

    public QuestionsService() {
        this(null, null);
    }

}
