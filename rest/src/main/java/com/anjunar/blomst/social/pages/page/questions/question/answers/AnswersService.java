package com.anjunar.blomst.social.pages.page.questions.question.answers;

import com.anjunar.blomst.social.pages.page.Answer;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.ddd.AbstractCriteriaSearchService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class AnswersService extends AbstractCriteriaSearchService<Answer, AnswersSearch> {

    @Inject
    public AnswersService(EntityManager entityManager, IdentityManager identityManager) {
        super(entityManager, identityManager);
    }

    public AnswersService() {
        this(null, null);
    }

}
