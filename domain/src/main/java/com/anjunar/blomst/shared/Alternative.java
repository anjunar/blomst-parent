package com.anjunar.blomst.shared;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.security.OwnerProvider;
import com.anjunar.common.security.User;
import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"property", "entity", "owner_id"}))
public class Alternative extends AbstractEntity implements OwnerProvider {

    private String value;

    private String property;

    private String entity;

    private String type;

    @ManyToOne
    private User owner;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
