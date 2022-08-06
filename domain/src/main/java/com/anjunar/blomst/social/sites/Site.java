package com.anjunar.blomst.social.sites;

import com.anjunar.common.security.Identity;

import javax.persistence.Entity;
import java.net.URL;

@Entity
public class Site extends Identity {

    private String name;

    private URL homepage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URL getHomepage() {
        return homepage;
    }

    public void setHomepage(URL homepage) {
        this.homepage = homepage;
    }
}
