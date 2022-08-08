package com.anjunar.blomst.social.pages.page.questions.question.answers.answer;

import com.anjunar.blomst.social.pages.page.Answer;
import com.anjunar.common.security.IdentityProvider;

import jakarta.persistence.EntityManager;
import java.util.UUID;

public class AnswerOwnerPredicate {

    private final IdentityProvider identityProvider;

    private final EntityManager entityManager;

    public AnswerOwnerPredicate(IdentityProvider identityProvider, EntityManager entityManager) {
        this.identityProvider = identityProvider;
        this.entityManager = entityManager;
    }

    public boolean apply(UUID id) {
        if (identityProvider.hasRole("Administrator")) {
            return true;
        }
        Answer question = entityManager.find(Answer.class, id);
        return question.getOwner().getId().equals(identityProvider.getUser().getId());
    }

}
