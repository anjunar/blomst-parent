package com.anjunar.blomst.social.sites;

import com.anjunar.common.security.Identity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Pattern;

import javax.annotation.RegEx;
import java.net.URL;

@Entity
public class Site extends Identity {

    private String name;

    private String phone;

    private String address;

    private URL homepage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public URL getHomepage() {
        return homepage;
    }

    public void setHomepage(URL homepage) {
        this.homepage = homepage;
    }
}
