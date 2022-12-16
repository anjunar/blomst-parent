package com.anjunar.blomst.social.sites;

import com.anjunar.blomst.shared.Alternative;
import com.anjunar.common.security.Identity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Pattern;

import javax.annotation.RegEx;
import java.net.URL;

@Entity
public class Site extends Identity {

    @ManyToOne
    private Alternative name;

    @ManyToOne
    private Alternative phone;

    private String address;

    @ManyToOne
    private Alternative homepage;

    public Alternative getName() {
        return name;
    }

    public void setName(Alternative name) {
        this.name = name;
    }

    public Alternative getPhone() {
        return phone;
    }

    public void setPhone(Alternative phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Alternative getHomepage() {
        return homepage;
    }

    public void setHomepage(Alternative homepage) {
        this.homepage = homepage;
    }
}
