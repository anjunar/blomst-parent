package com.anjunar.common.security;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.i18n.I18nType;
import com.anjunar.common.i18n.Language;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Type;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Entity
@Filter(name = "deletedFilter", condition = "deleted = false")
public class Category extends AbstractEntity {

    @Type(I18nType.class)
    @Column(name = "name")
    private final Map<Locale, String> i18nName = new HashMap<>();

    private String description;

    @ManyToOne
    private User owner;

    public Map<Locale, String> getI18nName() {
        return i18nName;
    }

    public String getName() {
        return i18nName.get(getIdentityStore().getLanguage());
    }

    public void setName(String name) {
        i18nName.put(getIdentityStore().getLanguage(), name);
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
