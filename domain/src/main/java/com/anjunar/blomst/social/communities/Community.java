package com.anjunar.blomst.social.communities;

import com.anjunar.common.security.Identity;

import jakarta.persistence.Entity;
import org.hibernate.annotations.Filter;

@Entity
@Filter(name = "deletedFilter", condition = "deleted = false")
public class Community extends Identity {

    private String name;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
