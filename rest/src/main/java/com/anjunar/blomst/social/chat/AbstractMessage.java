package com.anjunar.blomst.social.chat;

import com.anjunar.blomst.ApplicationWebSocketMessage;
import com.anjunar.blomst.shared.users.user.UserSelect;
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
@JsonTypeName("message")
public class AbstractMessage extends ApplicationWebSocketMessage {

    private Set<UUID> to;

    private UserSelect from;

    public Set<UUID> getTo() {
        return to;
    }

    public void setTo(Set<UUID> to) {
        this.to = to;
    }

    public UserSelect getFrom() {
        return from;
    }

    public void setFrom(UserSelect from) {
        this.from = from;
    }
}
