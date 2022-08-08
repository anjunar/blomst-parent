package com.anjunar.common.security;

import com.anjunar.common.ddd.AbstractEntity;
import org.hibernate.annotations.Filter;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "co_role")
@Filter(name = "deletedFilter", condition = "deleted = false")
public class Role extends AbstractEntity {

    private String name;

    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Permission> permissions = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

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

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> relationships) {
        this.users = relationships;
    }
}
