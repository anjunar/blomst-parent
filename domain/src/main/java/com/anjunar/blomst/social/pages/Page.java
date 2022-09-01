package com.anjunar.blomst.social.pages;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.security.User;
import com.anjunar.blomst.shared.Likeable;
import org.hibernate.annotations.Filter;
import org.hibernate.envers.*;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Entity
/*
@Audited
@AuditOverrides(value = {
        @AuditOverride(forClass = Page.class),
        @AuditOverride(forClass = Likeable.class),
        @AuditOverride(forClass = AbstractEntity.class),
})
*/
@Filter(name = "deletedFilter", condition = "deleted = false")
public class Page extends Likeable {

    private String title;

    @Embedded
    private Editor editor;

    @ManyToOne
//    @Audited(targetAuditMode = NOT_AUDITED)
    private User modifier;

//    @Audited(targetAuditMode = NOT_AUDITED)
    private Locale language;

    @OneToMany()
//    @Audited(targetAuditMode = NOT_AUDITED)
    private final Set<Page> links = new HashSet<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    public User getModifier() {
        return modifier;
    }

    public void setModifier(User modifier) {
        this.modifier = modifier;
    }

    public Locale getLanguage() {
        return language;
    }

    public void setLanguage(Locale language) {
        this.language = language;
    }

    public Set<Page> getLinks() {
        return links;
    }

}
