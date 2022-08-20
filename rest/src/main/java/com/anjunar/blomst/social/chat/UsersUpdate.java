package com.anjunar.blomst.social.chat;

import com.anjunar.blomst.ApplicationWebSocketMessage;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.List;

@JsonTypeName("users")
public class UsersUpdate extends ApplicationWebSocketMessage {

    private List<UserSelect> list;

    public List<UserSelect> getList() {
        return list;
    }

    public void setList(List<UserSelect> list) {
        this.list = list;
    }

}
