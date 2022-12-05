package com.anjunar.blomst.control.notifications;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.security.Identity;
import com.anjunar.common.security.User;

import jakarta.persistence.*;

@Entity
@Table(name = "notification")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class AbstractNotification extends AbstractEntity {

    private boolean acknowledge;

    @ManyToOne
    private User owner;

    public boolean isAcknowledge() {
        return acknowledge;
    }

    public void setAcknowledge(boolean acknowledge) {
        this.acknowledge = acknowledge;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

}
