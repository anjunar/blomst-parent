package com.anjunar.blomst.social.chat;

import com.anjunar.blomst.ApplicationWebSocketMessage;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.common.rest.schemamapper.ResourceMapper;
import com.anjunar.common.security.User;
import com.anjunar.common.websocket.OnCloseEvent;
import com.anjunar.common.websocket.OnMessageEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.persistence.EntityManager;
import jakarta.websocket.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ChatResourceStream {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public void onMessage(@Observes @OnMessageEvent TextMessage message, EntityManager entityManager) throws IOException {
        ResourceMapper mapper = new ResourceMapper();

        User from = entityManager.find(User.class, UUID.fromString(message.getSession().getUserPrincipal().getName()));

        message.setFrom(mapper.map(from, UserSelect.class));

        for (UUID uuid : message.getTo()) {
            if (message.getPool().containsKey(uuid.toString())) {
                Session transactionSession = message.getPool().get(uuid.toString());
                transactionSession.getBasicRemote()
                        .sendText(objectMapper.writeValueAsString(message));
            }
        }
    }

    public void onOpen(@Observes @OnMessageEvent UsersRead message, EntityManager entityManager) throws IOException {
        User loggedInUser = entityManager.find(User.class, UUID.fromString(message.getSession().getUserPrincipal().getName()));

        ResourceMapper mapper = new ResourceMapper();

        List<User> online = entityManager
                .createQuery("select c.to from UserConnection c where c.from.id = :id", User.class)
                .setParameter("id", UUID.fromString(message.getSession().getUserPrincipal().getName()))
                .getResultList()
                .stream()
                .filter((user) -> message.getPool().containsKey(user.getId().toString()))
                .toList();

        List<UserSelect> userSelects = new ArrayList<>();
        for (User user : online) {
            userSelects.add(mapper.map(user, UserSelect.class));
        }

        UsersUpdate usersUpdate = new UsersUpdate();
        usersUpdate.setList(userSelects);
        message.getSession().getBasicRemote().sendText(objectMapper.writeValueAsString(usersUpdate));

        for (User user : online) {
            Session onlineSession = message.getPool().get(user.getId().toString());
            StatusUpdate  statusUpdate = new StatusUpdate();
            statusUpdate.setStatus(StatusUpdate.Status.ONLINE);
            statusUpdate.setUser(mapper.map(loggedInUser, UserSelect.class));
            onlineSession.getBasicRemote().sendText(objectMapper.writeValueAsString(statusUpdate));
        }
    }

    public void onClose(@Observes @OnCloseEvent ApplicationWebSocketMessage message, EntityManager entityManager) throws IOException {
        User loggedInUser = entityManager.find(User.class, UUID.fromString(message.getSession().getUserPrincipal().getName()));

        ResourceMapper mapper = new ResourceMapper();

        List<User> online = entityManager
                .createQuery("select c.to from UserConnection c where c.from.id = :id", User.class)
                .setParameter("id", UUID.fromString(message.getSession().getUserPrincipal().getName()))
                .getResultList()
                .stream()
                .filter((user) -> message.getPool().containsKey(user.getId().toString()))
                .toList();

        for (User user : online) {
            Session onlineSession = message.getPool().get(user.getId().toString());
            StatusUpdate  statusUpdate = new StatusUpdate();
            statusUpdate.setStatus(StatusUpdate.Status.OFFLINE);
            statusUpdate.setUser(mapper.map(loggedInUser, UserSelect.class));
            onlineSession.getBasicRemote().sendText(objectMapper.writeValueAsString(statusUpdate));
        }
    }

}
