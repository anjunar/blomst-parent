package com.anjunar.blomst.social.chat;

import com.anjunar.blomst.ApplicationWebSocketMessage;
import com.anjunar.blomst.control.users.user.UserForm;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.List;

@JsonTypeName("chat-users-update")
public class UsersUpdate extends ApplicationWebSocketMessage {

    private List<UserForm> list;

    public List<UserForm> getList() {
        return list;
    }

    public void setList(List<UserForm> list) {
        this.list = list;
    }

}
