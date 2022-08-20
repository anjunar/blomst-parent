package com.anjunar.blomst.social.chat;

import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.common.rest.objectmapper.ResourceMapper;
import com.anjunar.common.security.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.ejb.Schedule;
import jakarta.ejb.Stateful;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/socket/chat")
public class ChatClientSocket {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static final Map<String, Session> pool = new ConcurrentHashMap<>();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    private final EntityManager entityManager;


    @Inject
    public ChatClientSocket(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ChatClientSocket() {
        this(null);
    }

    @OnMessage
    public void onStringMessage(String message, Session session) throws IOException {
        ResourceMapper mapper = new ResourceMapper();

        User from = entityManager.find(User.class, UUID.fromString(session.getUserPrincipal().getName()));

        TextMessage value = objectMapper.readerFor(TextMessage.class)
                .readValue(message, TextMessage.class);

        value.setFrom(mapper.map(from, UserSelect.class));

        for (UUID uuid : value.getTo()) {
            if (pool.containsKey(uuid.toString())) {
                Session transactionSession = pool.get(uuid.toString());
                transactionSession.getBasicRemote()
                        .sendText(objectMapper.writeValueAsString(value));
            }
        }
    }

    @OnOpen
    public void onOpen(Session session) throws IOException {
        pool.put(session.getUserPrincipal().getName(), session);

        User loggedInUser = entityManager.find(User.class, UUID.fromString(session.getUserPrincipal().getName()));

        ResourceMapper mapper = new ResourceMapper();

        List<User> online = entityManager
                .createQuery("select c.to from UserConnection c where c.from.id = :id", User.class)
                .setParameter("id", UUID.fromString(session.getUserPrincipal().getName()))
                .getResultList()
                .stream()
                .filter((user) -> pool.containsKey(user.getId().toString()))
                .toList();

        List<UserSelect> userSelects = new ArrayList<>();
        for (User user : online) {
            userSelects.add(mapper.map(user, UserSelect.class));
        }

        UsersUpdate usersUpdate = new UsersUpdate();
        usersUpdate.setList(userSelects);
        session.getBasicRemote().sendText(objectMapper.writeValueAsString(usersUpdate));

        for (User user : online) {
            Session onlineSession = pool.get(user.getId().toString());
            StatusUpdate  statusUpdate = new StatusUpdate();
            statusUpdate.setStatus(StatusUpdate.Status.ONLINE);
            statusUpdate.setUser(mapper.map(loggedInUser, UserSelect.class));
            onlineSession.getBasicRemote().sendText(objectMapper.writeValueAsString(statusUpdate));
        }

    }

    @OnClose
    public void onClose(Session session) throws IOException {
        User loggedInUser = entityManager.find(User.class, UUID.fromString(session.getUserPrincipal().getName()));

        ResourceMapper mapper = new ResourceMapper();

        List<User> online = entityManager
                .createQuery("select c.to from UserConnection c where c.from.id = :id", User.class)
                .setParameter("id", UUID.fromString(session.getUserPrincipal().getName()))
                .getResultList()
                .stream()
                .filter((user) -> pool.containsKey(user.getId().toString()))
                .toList();

        for (User user : online) {
            Session onlineSession = pool.get(user.getId().toString());
            StatusUpdate  statusUpdate = new StatusUpdate();
            statusUpdate.setStatus(StatusUpdate.Status.OFFLINE);
            statusUpdate.setUser(mapper.map(loggedInUser, UserSelect.class));
            onlineSession.getBasicRemote().sendText(objectMapper.writeValueAsString(statusUpdate));
        }

        pool.remove(session.getUserPrincipal().getName());
    }

}
