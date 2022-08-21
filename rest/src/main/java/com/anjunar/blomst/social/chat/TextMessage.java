package com.anjunar.blomst.social.chat;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("chat-text-message")
public class TextMessage extends AbstractMessage {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
