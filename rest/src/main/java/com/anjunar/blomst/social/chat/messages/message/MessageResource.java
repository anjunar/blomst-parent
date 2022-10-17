package com.anjunar.blomst.social.chat.messages.message;

import com.anjunar.blomst.WebSocketSessions;
import com.anjunar.blomst.social.chat.ChatMessage;
import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.security.IdentityStore;
import com.anjunar.common.security.User;
import com.anjunar.common.websocket.WebSocketProtocol;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.websocket.Session;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import java.util.*;

@ApplicationScoped
@Path("social/chat/messages/message")
public class MessageResource {

    private final EntityManager entityManager;

    private final IdentityStore identityStore;

    private final WebSocketSessions sessions;

    private final WebSocketProtocol webSocketProtocol;

    @Inject
    public MessageResource(EntityManager entityManager, IdentityStore identityStore, WebSocketSessions sessions, WebSocketProtocol webSocketProtocol) {
        this.entityManager = entityManager;
        this.identityStore = identityStore;
        this.sessions = sessions;
        this.webSocketProtocol = webSocketProtocol;
    }

    public MessageResource() {
        this(null, null, null, null);
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @RolesAllowed({"Administrator", "User"})
    public ResponseOk send(MessageForm message) {

        User from = identityStore.getUser();

        final List<User> recipients = new ArrayList<>();
        for (UUID uuid : message.getTo()) {
            recipients.add(entityManager.find(User.class, uuid));
        }

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setText(message.getText());
        chatMessage.setOwner(from);
        chatMessage.getParticipants().add(from);
        chatMessage.getParticipants().addAll(recipients);
        entityManager.persist(chatMessage);

        for (UUID uuid : message.getTo()) {
            if (sessions.getPool().containsKey(uuid.toString())) {
                Session transactionSession = sessions.getPool().get(uuid.toString());

                Set<UUID> participants = new HashSet<>();
                participants.add(from.getId());
                participants.addAll(recipients.stream().map(AbstractEntity::getId).toList());

                MessageStream stream = new MessageStream();
                stream.setFrom(from.getId());
                stream.setParticipants(participants);
                stream.setText(message.getText());

                webSocketProtocol.protocol("chat-message", stream, transactionSession);
            }
        }

        return new ResponseOk();
    }

}
