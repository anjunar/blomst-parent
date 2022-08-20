package com.anjunar.blomst;

import com.anjunar.blomst.social.chat.AbstractMessage;
import com.anjunar.blomst.social.chat.StatusUpdate;
import com.anjunar.blomst.social.chat.UsersUpdate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.websocket.Session;

import java.util.Map;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(AbstractMessage.class),
        @JsonSubTypes.Type(StatusUpdate.class),
        @JsonSubTypes.Type(UsersUpdate.class),
})
public class ApplicationWebSocketMessage {

    @JsonIgnore
    private Session session;

    @JsonIgnore
    private Map<String, Session> pool;

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
