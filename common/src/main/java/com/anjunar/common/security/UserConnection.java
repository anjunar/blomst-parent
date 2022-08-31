package com.anjunar.common.security;

import com.anjunar.common.ddd.AbstractEntity;
import org.hibernate.annotations.Filter;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity(name = "UserConnection")
@Filter(name = "deletedFilter", condition = "deleted = false")
public class UserConnection extends AbstractEntity {

    @ManyToOne
    private User from;

    @ManyToOne
    private Category category;

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

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }
}
