package com.anjunar.blomst.security.login;

import com.anjunar.common.rest.api.AbstractSchemaEntity;

public class LoginResponse extends AbstractSchemaEntity {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
