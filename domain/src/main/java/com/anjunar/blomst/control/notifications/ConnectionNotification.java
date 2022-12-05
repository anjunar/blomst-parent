package com.anjunar.blomst.control.notifications;

import com.anjunar.common.security.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class ConnectionNotification extends AbstractNotification {

    @ManyToOne
    private User to;

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

}
