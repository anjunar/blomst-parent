package com.anjunar.blomst.social.chat;

import com.anjunar.blomst.ApplicationWebSocketMessage;
import com.anjunar.blomst.control.users.user.UserForm;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("chat-status")
public class StatusUpdate extends ApplicationWebSocketMessage {

    public enum Status {

        ONLINE,
        OFFLINE;

    }

    private Status status;

    private UserForm user;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UserForm getUser() {
        return user;
    }

    public void setUser(UserForm user) {
        this.user = user;
    }
}
