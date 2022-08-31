package com.anjunar.common.security;

import com.anjunar.common.security.Category;
import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.security.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.Set;

@Entity
public class EntitySchema extends AbstractEntity {

    @ManyToOne
    private User owner;

    private Class<?> entity;

    private String property;

    @OneToMany
    private Set<Category> visibility;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Class<?> getEntity() {
        return entity;
    }

    public void setEntity(Class<?> entity) {
        this.entity = entity;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Set<Category> getVisibility() {
        return visibility;
    }

    public void setVisibility(Set<Category> visibility) {
        this.visibility = visibility;
    }
}
