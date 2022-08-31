package com.anjunar.common.mail;

import com.anjunar.common.ddd.AbstractEntity;
import org.hibernate.annotations.Filter;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.util.Locale;

@Entity
@Filter(name = "deletedFilter", condition = "deleted = false")
public class Template extends AbstractEntity {

    private String name;

    private Locale language;

    @Lob
    private String html;

    @Lob
    private String text;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Locale getLanguage() {
        return language;
    }

    public void setLanguage(Locale language) {
        this.language = language;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
