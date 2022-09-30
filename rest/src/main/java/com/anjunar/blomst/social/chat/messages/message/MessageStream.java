package com.anjunar.blomst.social.chat.messages.message;

import java.util.Set;
import java.util.UUID;

public class MessageStream {

    private String text;

    private UUID from;

    private Set<UUID> participants;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UUID getFrom() {
        return from;
    }

    public void setFrom(UUID from) {
        this.from = from;
    }

    public Set<UUID> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<UUID> participants) {
        this.participants = participants;
    }
}
