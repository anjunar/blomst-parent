package com.anjunar.common.security;

import com.anjunar.common.ddd.AbstractRight;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class UserRight extends AbstractRight<User> {

    @ManyToOne
    private User source;

    public User getSource() {
        return source;
    }

    public void setSource(User owner) {
        this.source = owner;
    }

}
