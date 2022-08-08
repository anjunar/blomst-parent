package com.anjunar.blomst.control.users;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.security.User;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "do_resume")
public class Resume extends AbstractEntity {

    @ManyToOne
    private User owner;

    @OrderBy("end desc")
    @OneToMany(cascade = CascadeType.ALL)
    private final List<ResumeItem> items = new ArrayList<>();

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<ResumeItem> getItems() {
        return items;
    }
}
