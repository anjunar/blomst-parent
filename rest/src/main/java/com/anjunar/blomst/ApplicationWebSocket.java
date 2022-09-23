package com.anjunar.blomst;

import com.anjunar.common.security.IdentityStore;
import com.anjunar.common.security.User;
import com.anjunar.common.websocket.OnCloseEvent;
import com.anjunar.common.websocket.OnMessageEvent;
import com.anjunar.common.websocket.OnOpenEvent;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.*;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.jboss.weld.context.bound.BoundRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/socket")
public class ApplicationWebSocket {

    private static final Logger log = LoggerFactory.getLogger(ApplicationWebSocket.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static final Map<String, Session> pool = new ConcurrentHashMap<>();

    static {
        objectMapper.registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private final EntityManager entityManager;

    private final Event<ApplicationWebSocketMessage> messageEvent;

    private final Event<ApplicationWebSocketMessage> openEvent;

    private final Event<ApplicationWebSocketMessage> closeEvent;

    private final BoundRequestContext requestContext;

    private final UserTransaction transaction;


    @Inject
    public ApplicationWebSocket(EntityManager entityManager,
                                @OnMessageEvent Event<ApplicationWebSocketMessage> messageEvent,
                                @OnOpenEvent Event<ApplicationWebSocketMessage> openEvent,
                                @OnCloseEvent Event<ApplicationWebSocketMessage> closeEvent,
                                BoundRequestContext requestContext,
                                UserTransaction transaction) {
        this.entityManager = entityManager;
        this.messageEvent = messageEvent;
        this.openEvent = openEvent;
        this.closeEvent = closeEvent;
        this.requestContext = requestContext;
        this.transaction = transaction;
    }

    public ApplicationWebSocket() {
        this(null, null, null, null, null, null);
    }

    @OnMessage
    public void onStringMessage(String message, Session session) throws RuntimeException {

        try {
            transaction.begin();

            User user = entityManager.find(User.class, UUID.fromString(session.getUserPrincipal().getName()));

            requestContext.associate(new LinkedHashMap<>());
            requestContext.activate();

            IdentityStore identityStore = CDI.current().select(IdentityStore.class).get();
            identityStore.setUser(user);

            ApplicationWebSocketMessage socketMessage = objectMapper
                    .readerFor(ApplicationWebSocketMessage.class)
                    .readValue(message, ApplicationWebSocketMessage.class);

            socketMessage.setSession(session);
            socketMessage.setPool(pool);

            messageEvent.fire(socketMessage);

            requestContext.invalidate();
            requestContext.deactivate();

            transaction.commit();
        } catch (NotSupportedException | SystemException | IOException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
            try {
                log.error(e.getLocalizedMessage(), e);
                requestContext.invalidate();
                requestContext.deactivate();
                transaction.rollback();
            } catch (SystemException ex) {
                log.error(ex.getLocalizedMessage(), ex);
            }
        }
    }

    @OnOpen
    public void onOpen(Session session)  {
        if (session.getUserPrincipal() != null) {

            try {
                transaction.begin();

                User user = entityManager.find(User.class, UUID.fromString(session.getUserPrincipal().getName()));

                requestContext.associate(new LinkedHashMap<>());
                requestContext.activate();

                IdentityStore identityStore = CDI.current().select(IdentityStore.class).get();
                identityStore.setUser(user);

                pool.put(session.getUserPrincipal().getName(), session);

                ApplicationWebSocketMessage socketMessage = new ApplicationWebSocketMessage();
                socketMessage.setSession(session);
                socketMessage.setPool(pool);

                openEvent.fire(socketMessage);

                requestContext.invalidate();
                requestContext.deactivate();

                transaction.commit();
            } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
                try {
                    log.error(e.getLocalizedMessage(), e);
                    requestContext.invalidate();
                    requestContext.deactivate();
                    transaction.rollback();
                } catch (SystemException ex) {
                    log.error(ex.getLocalizedMessage(), ex);
                }
            }
        }
    }

    @OnClose
    public void onClose(Session session)  {
        try {
            transaction.begin();

            requestContext.associate(new LinkedHashMap<>());
            requestContext.activate();

            ApplicationWebSocketMessage socketMessage = new ApplicationWebSocketMessage();
            socketMessage.setSession(session);
            socketMessage.setPool(pool);

            closeEvent.fire(socketMessage);

            IdentityStore identityStore = CDI.current().select(IdentityStore.class).get();
            identityStore.setUser(null);

            requestContext.invalidate();
            requestContext.deactivate();

            pool.remove(session.getUserPrincipal().getName());

            transaction.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
            try {
                log.error(e.getLocalizedMessage(), e);
                requestContext.invalidate();
                requestContext.deactivate();
                transaction.rollback();
            } catch (SystemException ex) {
                log.error(ex.getLocalizedMessage(), ex);
            }
        }
    }

}
