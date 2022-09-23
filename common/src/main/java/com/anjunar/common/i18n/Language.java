package com.anjunar.common.i18n;

import com.anjunar.common.ddd.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import org.hibernate.annotations.Type;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Entity
public class Language extends AbstractEntity {

    private Locale locale;

    @Type(I18nType.class)
    @Column(name = "name")
    private final Map<Locale, String> i18nName = new HashMap<>();

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getName() {
        return i18nName.get(getIdentityStore().getLanguage());
    }

    public Map<Locale, String> getI18nName() {
        return i18nName;
    }

}
