package com.anjunar.blomst.control.notifications;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.security.Identity;
import com.anjunar.common.security.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "do_notification")
@Entity
public class Notification extends AbstractEntity {

    private String text;

    private String description;

    private boolean acknowledge;

    @ManyToOne
    private Identity source;

    @ManyToOne
    private User owner;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Identity getSource() {
        return source;
    }

    public void setSource(Identity source) {
        this.source = source;
    }

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
