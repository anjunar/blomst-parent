package com.anjunar.blomst.control.users;

import com.anjunar.common.ddd.AbstractRight;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class AddressRight extends AbstractRight<Address> {

    @OneToOne
    private Address source;

    @Override
    public Address getSource() {
        return source;
    }

    public void setSource(Address source) {
        this.source = source;
    }

}
