package com.anjunar.blomst.social.chat;

import com.anjunar.blomst.shared.users.user.UserSelect;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("status")
public class StatusUpdate extends WebSocketMessage {

    public enum Status {

        ONLINE,
        OFFLINE;

    }

    private Status status;

    private UserSelect user;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UserSelect getUser() {
        return user;
    }

    public void setUser(UserSelect user) {
        this.user = user;
    }
}
