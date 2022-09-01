package com.anjunar.common.security;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.i18n.Translations;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Filter(name = "deletedFilter", condition = "deleted = false")
public class Category extends AbstractEntity {

    @JdbcTypeCode(SqlTypes.JSON)
    private Translations name;

    private String description;

    @ManyToOne
    private User owner;

    public Translations getName() {
        return name;
    }

    public void setName(Translations name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
