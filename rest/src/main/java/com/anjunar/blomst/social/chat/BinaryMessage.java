package com.anjunar.blomst.social.chat;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("chat-binary-message")
public class BinaryMessage extends AbstractMessage {

    private byte[] data;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
