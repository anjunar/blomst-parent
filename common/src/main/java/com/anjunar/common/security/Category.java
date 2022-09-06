package com.anjunar.common.security;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.i18n.I18nType;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Inject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Entity
@Filter(name = "deletedFilter", condition = "deleted = false")
public class Category extends AbstractEntity {

    @Type(I18nType.class)
    @Column(name = "name", columnDefinition = "json")
    private final Map<Locale, String> i18nName = new HashMap<>();

    private String description;

    @ManyToOne
    private User owner;

    public Map<Locale, String> getI18nName() {
        return i18nName;
    }

    public String getName() {
        Locale language = getIdentityProvider().getLanguage();
        return i18nName.get(language);
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
