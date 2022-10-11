package com.anjunar.blomst.control.users;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.security.OwnerProvider;
import com.anjunar.common.security.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Addresses extends AbstractEntity implements OwnerProvider {

    @ManyToOne
    private User owner;

    @OrderBy("end desc")
    @OneToMany(cascade = CascadeType.ALL)
    private final List<Address> items = new ArrayList<>();

    @Override
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Address> getItems() {
        return items;
    }

}
