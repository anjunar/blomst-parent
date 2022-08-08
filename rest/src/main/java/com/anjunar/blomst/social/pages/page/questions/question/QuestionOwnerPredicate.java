package com.anjunar.blomst.social.pages.page.questions.question;

import com.anjunar.blomst.social.pages.page.Question;
import com.anjunar.common.security.IdentityProvider;

import jakarta.persistence.EntityManager;
import java.util.UUID;

public class QuestionOwnerPredicate {

    private final IdentityProvider identityProvider;

    private final EntityManager entityManager;

    public QuestionOwnerPredicate(IdentityProvider identityProvider, EntityManager entityManager) {
        this.identityProvider = identityProvider;
        this.entityManager = entityManager;
    }

    public boolean apply(UUID id) {
        if (identityProvider.hasRole("Administrator")) {
            return true;
        }
        Question question = entityManager.find(Question.class, id);
        return question.getOwner().getId().equals(identityProvider.getUser().getId());
    }
}
