package com.anjunar.common.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.websocket.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class WebSocketProtocol {

    private static final Logger log = LoggerFactory.getLogger(WebSocketProtocol.class);

    private final ObjectMapper objectMapper;

    @Inject
    public WebSocketProtocol(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public WebSocketProtocol() {
        this(null);
    }

    public void protocol(String name, Session session) {
        try {
            session.getBasicRemote().sendText(name + "()");
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public <E> void protocol(String name, E json, Session session) {
        try {
            String text = name + "(" + objectMapper.writeValueAsString(json) + ")";
            session.getBasicRemote().sendText(text);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
