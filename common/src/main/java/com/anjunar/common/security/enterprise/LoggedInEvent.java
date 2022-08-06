package com.anjunar.common.security.enterprise;

import com.anjunar.common.security.User;

public class LoggedInEvent {

    private final User user;

    public LoggedInEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
