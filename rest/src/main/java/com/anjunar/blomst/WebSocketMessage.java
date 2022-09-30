package com.anjunar.blomst;

import jakarta.websocket.Session;

import java.util.Map;

public class WebSocketMessage {

    private String message;

    private Session session;

    private Map<String, Session> pool;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Map<String, Session> getPool() {
        return pool;
    }

    public void setPool(Map<String, Session> pool) {
        this.pool = pool;
    }

}
