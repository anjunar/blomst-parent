package com.anjunar.blomst.social.communities;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.security.Role;
import com.anjunar.common.security.User;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity(name = "CommunitiesConnection")
public class CommunitiesConnection extends AbstractEntity {

    @ManyToOne
    private User from;

    private Status status;

    @ManyToOne
    private Role role;

    @ManyToOne
    private Community to;

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Community getTo() {
        return to;
    }

    public void setTo(Community to) {
        this.to = to;
    }
}
