package com.anjunar.blomst.social.chat;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(AbstractMessage.class),
        @JsonSubTypes.Type(StatusUpdate.class),
        @JsonSubTypes.Type(UsersUpdate.class),
})
public abstract class WebSocketMessage { }
