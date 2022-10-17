package com.anjunar.common.security;

import com.anjunar.common.ddd.AbstractColumnRight;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class UserRight extends AbstractColumnRight<User> {

    @ManyToOne
    private User source;

    public User getSource() {
        return source;
    }

    public void setSource(User owner) {
        this.source = owner;
    }

}
