package com.anjunar.blomst;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class WebSocketSessions {

    private final Map<String, Session> pool = new ConcurrentHashMap<>();

    public Map<String, Session> getPool() {
        return pool;
    }

}
