package com.anjunar.blomst.social.sites;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.security.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "do_site_connection")
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
