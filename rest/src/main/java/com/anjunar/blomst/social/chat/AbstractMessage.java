package com.anjunar.blomst.social.chat;

import com.anjunar.blomst.ApplicationWebSocketMessage;
import com.anjunar.blomst.control.users.user.UserForm;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Set;
import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(TextMessage.class),
        @JsonSubTypes.Type(BinaryMessage.class),
})
@JsonTypeName("chat-message")
public class AbstractMessage extends ApplicationWebSocketMessage {

    private Set<UUID> to;

    private UserForm from;

    public Set<UUID> getTo() {
        return to;
    }

    public void setTo(Set<UUID> to) {
        this.to = to;
    }

    public UserForm getFrom() {
        return from;
    }

    public void setFrom(UserForm from) {
        this.from = from;
    }
}
