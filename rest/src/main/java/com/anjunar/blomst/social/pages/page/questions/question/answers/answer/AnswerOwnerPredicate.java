package com.anjunar.blomst.social.pages.page.questions.question.answers.answer;

import com.anjunar.blomst.social.pages.page.Answer;
import com.anjunar.common.security.IdentityManager;

import jakarta.persistence.EntityManager;
import java.util.UUID;

public class AnswerOwnerPredicate {

    private final IdentityManager identityManager;

    private final EntityManager entityManager;

    public AnswerOwnerPredicate(IdentityManager identityManager, EntityManager entityManager) {
        this.identityManager = identityManager;
        this.entityManager = entityManager;
    }

    public boolean apply(UUID id) {
        if (identityManager.hasRole("Administrator")) {
            return true;
        }
        Answer question = entityManager.find(Answer.class, id);
        return question.getOwner().getId().equals(identityManager.getUser().getId());
    }

}
