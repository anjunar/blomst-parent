package com.anjunar.common.security.enterprise;

import jakarta.security.enterprise.credential.AbstractClearableCredential;
import jakarta.security.enterprise.credential.Password;

public class EmailCredential extends AbstractClearableCredential {

    private final String email;

    private final Password password;

    public EmailCredential(String email, Password password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }

    @Override
    protected void clearCredential() {
        password.clear();
    }

    public String getPasswordAsString() {
        return String.valueOf(getPassword().getValue());
    }


}
