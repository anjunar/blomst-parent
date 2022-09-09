package com.anjunar.blomst.social.pages.page.questions.question;

import com.anjunar.blomst.social.pages.page.Question;
import com.anjunar.common.security.IdentityManager;

import jakarta.persistence.EntityManager;
import java.util.UUID;

public class QuestionOwnerPredicate {

    private final IdentityManager identityManager;

    private final EntityManager entityManager;

    public QuestionOwnerPredicate(IdentityManager identityManager, EntityManager entityManager) {
        this.identityManager = identityManager;
        this.entityManager = entityManager;
    }

    public boolean apply(UUID id) {
        if (identityManager.hasRole("Administrator")) {
            return true;
        }
        Question question = entityManager.find(Question.class, id);
        return question.getOwner().getId().equals(identityManager.getUser().getId());
    }
}
