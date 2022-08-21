package com.anjunar.blomst;

import com.anjunar.common.websocket.OnCloseEvent;
import com.anjunar.common.websocket.OnMessageEvent;
import com.anjunar.common.websocket.OnOpenEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/socket")
public class ApplicationWebSocket {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static final Map<String, Session> pool = new ConcurrentHashMap<>();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    private final Event<ApplicationWebSocketMessage> messageEvent;

    private final Event<ApplicationWebSocketMessage> openEvent;

    private final Event<ApplicationWebSocketMessage> closeEvent;


    @Inject
    public ApplicationWebSocket(@OnMessageEvent Event<ApplicationWebSocketMessage> messageEvent,
                                @OnOpenEvent Event<ApplicationWebSocketMessage> openEvent,
                                @OnCloseEvent Event<ApplicationWebSocketMessage> closeEvent) {
        this.messageEvent = messageEvent;
        this.openEvent = openEvent;
        this.closeEvent = closeEvent;
    }

    public ApplicationWebSocket() {
        this(null, null, null);
    }

    @OnMessage
    public void onStringMessage(String message, Session session) throws IOException {

        ApplicationWebSocketMessage socketMessage = objectMapper
                .readerFor(ApplicationWebSocketMessage.class)
                .readValue(message, ApplicationWebSocketMessage.class);

        socketMessage.setSession(session);
        socketMessage.setPool(pool);

        messageEvent.fire(socketMessage);
    }

    @OnOpen
    public void onOpen(Session session) {
        pool.put(session.getUserPrincipal().getName(), session);

        ApplicationWebSocketMessage socketMessage = new ApplicationWebSocketMessage();
        socketMessage.setSession(session);
        socketMessage.setPool(pool);

        openEvent.fire(socketMessage);
    }

    @OnClose
    public void onClose(Session session)  {
        ApplicationWebSocketMessage socketMessage = new ApplicationWebSocketMessage();
        socketMessage.setSession(session);
        socketMessage.setPool(pool);

        closeEvent.fire(socketMessage);

        pool.remove(session.getUserPrincipal().getName());
    }

}
