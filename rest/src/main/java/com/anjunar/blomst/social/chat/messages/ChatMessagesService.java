package com.anjunar.blomst.social.chat.messages;

import com.anjunar.blomst.social.chat.ChatMessage;
import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.IdentityManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class ChatMessagesService extends AbstractCriteriaSearchService<ChatMessage, ChatMessagesSearch> {

    @Inject
    public ChatMessagesService(EntityManager entityManager, IdentityManager identityManager) {
        super(entityManager, identityManager);
    }

    public ChatMessagesService() {
        this(null, null);
    }

}
