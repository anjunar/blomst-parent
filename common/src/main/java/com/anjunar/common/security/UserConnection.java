package com.anjunar.common.security;

import com.anjunar.common.ddd.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;

@Entity
public class UserConnection extends AbstractEntity {

    public enum ConnectionStatus {
        NONE,
        INITIATED,
        PENDING,
        ACCEPTED,
        DECLINED,
        CANCELED
    }

    @ManyToOne
    private User from;

    @ManyToOne
    private Category category;

    @Enumerated(EnumType.STRING)
    private ConnectionStatus status;

    @ManyToOne
    private User to;

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ConnectionStatus getStatus() {
        return status;
    }

    public void setStatus(ConnectionStatus status) {
        this.status = status;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }
}
