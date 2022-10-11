package com.anjunar.blomst.control.users;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.ddd.AbstractRight;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class AddressesRight extends AbstractRight<Addresses> {

    @ManyToOne
    private Addresses source;

    @Override
    public Addresses getSource() {
        return source;
    }

    public void setSource(Addresses source) {
        this.source = source;
    }

}
