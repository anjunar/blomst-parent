package com.anjunar.blomst.control.users.user;

import java.util.UUID;

public interface UserSelect {

    UUID getId();

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);
}
