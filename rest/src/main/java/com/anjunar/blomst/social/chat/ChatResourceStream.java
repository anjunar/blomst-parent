package com.anjunar.blomst.social.chat;

import com.anjunar.blomst.WebSocketMessage;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.chat.messages.message.MessageForm;
import com.anjunar.common.rest.api.Form;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.security.User;
import com.anjunar.common.websocket.OnCloseEvent;
import com.anjunar.common.websocket.OnMessageEvent;
import com.anjunar.common.websocket.OnOpenEvent;
import com.anjunar.common.websocket.WebSocketProtocol;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.websocket.Session;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ChatResourceStream {

    private final ObjectMapper objectMapper;
    private final WebSocketProtocol webSocketProtocol;

    @Inject
    public ChatResourceStream(ObjectMapper objectMapper, WebSocketProtocol webSocketProtocol) {
        this.objectMapper = objectMapper;
        this.webSocketProtocol = webSocketProtocol;
    }

    public ChatResourceStream() {
        this(null, null);
    }

    public void onStartTyping(@Observes @OnMessageEvent("chat-start-typing") WebSocketMessage message, EntityManager entityManager, ResourceEntityMapper entityMapper) throws IOException {
        MessageForm value = objectMapper.readerFor(MessageForm.class)
                .readValue(message.getMessage(), MessageForm.class);

        User user = entityManager.find(User.class, value.getFrom());
        Form<UserSelect> userSelect = entityMapper.map(user, new Form<>() {});
        String json = objectMapper.writeValueAsString(userSelect);

        for (UUID uuid : value.getTo()) {
            message.getPool().get(uuid.toString())
                    .getBasicRemote()
                    .sendText("chat-start-typing(" + json +")");
        }
    }

    public void onEndTyping(@Observes @OnMessageEvent("chat-end-typing") WebSocketMessage message) throws IOException {
        MessageForm value = objectMapper.readerFor(MessageForm.class)
                .readValue(message.getMessage(), MessageForm.class);

        for (UUID uuid : value.getTo()) {
            message.getPool().get(uuid.toString())
                    .getBasicRemote()
                    .sendText("chat-start-typing()");
        }
    }

    public void onOpen(@Observes @OnOpenEvent WebSocketMessage message, EntityManager entityManager) {
        statusUpdate(message, entityManager);
    }

    public void onClose(@Observes @OnCloseEvent WebSocketMessage message, EntityManager entityManager) {
        statusUpdate(message, entityManager);
    }

    private void statusUpdate(WebSocketMessage message, EntityManager entityManager) {
        List<User> online = entityManager
                .createQuery("select c.to from UserConnection c where c.from.id = :id", User.class)
                .setParameter("id", UUID.fromString(message.getSession().getUserPrincipal().getName()))
                .getResultList()
                .stream()
                .filter((user) -> message.getPool().containsKey(user.getId().toString()))
                .toList();

        for (User user : online) {
            Session onlineSession = message.getPool().get(user.getId().toString());
            webSocketProtocol.protocol("chat-status", onlineSession);
        }
    }

}
