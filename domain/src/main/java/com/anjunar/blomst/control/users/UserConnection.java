package com.anjunar.blomst.control.users;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.security.User;
import org.hibernate.annotations.Filter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "do_connection")
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
