package com.anjunar.common.security;

import javax.persistence.Embeddable;
import javax.validation.constraints.Email;

@Embeddable
public class EmailType {

    @Email
    private String value;

    private boolean confirmed;

    private String hash;

    public String getValue() {
        return value;
    }

    public void setValue(String email) {
        this.value = email;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
