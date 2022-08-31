package com.anjunar.blomst.social.sites;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.security.User;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
public class SiteConnection extends AbstractEntity {

    @ManyToOne
    private User from;

    @ManyToOne
    private Site to;

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public Site getTo() {
        return to;
    }

    public void setTo(Site to) {
        this.to = to;
    }
}
