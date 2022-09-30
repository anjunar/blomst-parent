package com.anjunar.blomst.social.chat.messages.message;

import java.util.Set;
import java.util.UUID;

public class MessageForm {

    private String text;

    private UUID from;

    private Set<UUID> to;

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

    public Set<UUID> getTo() {
        return to;
    }

    public void setTo(Set<UUID> to) {
        this.to = to;
    }
}
