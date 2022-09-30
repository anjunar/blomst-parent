package com.anjunar.blomst;

import com.anjunar.common.security.IdentityStore;
import com.anjunar.common.security.User;
import com.anjunar.common.websocket.*;
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

import java.util.LinkedHashMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ServerEndpoint("/socket")
public class WebSocket {

    private static final Logger log = LoggerFactory.getLogger(WebSocket.class);

    private final EntityManager entityManager;

    private final Event<WebSocketMessage> messageEvent;

    private final Event<WebSocketMessage> openEvent;

    private final Event<WebSocketMessage> closeEvent;

    private final BoundRequestContext requestContext;

    private final UserTransaction transaction;

    private final WebSocketSessions sessions;


    @Inject
    public WebSocket(EntityManager entityManager,
                     Event<WebSocketMessage> messageEvent,
                     @OnOpenEvent Event<WebSocketMessage> openEvent,
                     @OnCloseEvent Event<WebSocketMessage> closeEvent,
                     BoundRequestContext requestContext,
                     UserTransaction transaction,
                     WebSocketSessions sessions) {
        this.entityManager = entityManager;
        this.messageEvent = messageEvent;
        this.openEvent = openEvent;
        this.closeEvent = closeEvent;
        this.requestContext = requestContext;
        this.transaction = transaction;
        this.sessions = sessions;
    }

    public WebSocket() {
        this(null, null, null, null, null, null, null);
    }

    @OnMessage
    public void onStringMessage(String message, Session session) throws RuntimeException {

        try {
            requestContext.associate(new LinkedHashMap<>());
            requestContext.activate();

            transaction.begin();

            Pattern pattern = Pattern.compile("([\\w-]+)\\((.*)\\)");
            Matcher matcher = pattern.matcher(message);

            if (matcher.matches()) {
                User user = entityManager.find(User.class, UUID.fromString(session.getUserPrincipal().getName()));

                IdentityStore identityStore = CDI.current().select(IdentityStore.class).get();
                identityStore.setUser(user);

                WebSocketMessage socketMessage = new WebSocketMessage();
                socketMessage.setMessage(matcher.group(2));
                socketMessage.setSession(session);
                socketMessage.setPool(sessions.getPool());

                messageEvent.select(new OnMessageEventLiteral(matcher.group(1))).fire(socketMessage);
            }

            transaction.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
            try {
                log.error(e.getLocalizedMessage(), e);
                transaction.rollback();
            } catch (SystemException ex) {
                log.error(ex.getLocalizedMessage(), ex);
            }
        } finally {
            requestContext.invalidate();
            requestContext.deactivate();
        }
    }

    @OnOpen
    public void onOpen(Session session)  {
        if (session.getUserPrincipal() != null) {

            try {
                requestContext.associate(new LinkedHashMap<>());
                requestContext.activate();

                transaction.begin();

                User user = entityManager.find(User.class, UUID.fromString(session.getUserPrincipal().getName()));

                IdentityStore identityStore = CDI.current().select(IdentityStore.class).get();
                identityStore.setUser(user);

                sessions.getPool().put(session.getUserPrincipal().getName(), session);

                WebSocketMessage socketMessage = new WebSocketMessage();
                socketMessage.setSession(session);
                socketMessage.setPool(sessions.getPool());

                openEvent.fire(socketMessage);

                transaction.commit();
            } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
                try {
                    log.error(e.getLocalizedMessage(), e);
                    transaction.rollback();
                } catch (SystemException ex) {
                    log.error(ex.getLocalizedMessage(), ex);
                }
            } finally {
                requestContext.invalidate();
                requestContext.deactivate();
            }
        }
    }

    @OnClose
    public void onClose(Session session)  {
        try {
            requestContext.associate(new LinkedHashMap<>());
            requestContext.activate();

            transaction.begin();

            WebSocketMessage socketMessage = new WebSocketMessage();
            socketMessage.setSession(session);
            socketMessage.setPool(sessions.getPool());

            closeEvent.fire(socketMessage);

            IdentityStore identityStore = CDI.current().select(IdentityStore.class).get();
            identityStore.setUser(null);

            requestContext.invalidate();
            requestContext.deactivate();

            sessions.getPool().remove(session.getUserPrincipal().getName());

            transaction.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
            try {
                log.error(e.getLocalizedMessage(), e);
                transaction.rollback();
            } catch (SystemException ex) {
                log.error(ex.getLocalizedMessage(), ex);
            }
        } finally {
            requestContext.invalidate();
            requestContext.deactivate();
        }
    }

}
