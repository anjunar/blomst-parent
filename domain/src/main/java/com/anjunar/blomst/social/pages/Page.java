package com.anjunar.blomst.social.pages;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.i18n.Language;
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

    @Column(columnDefinition="TEXT")
    private String title;

    @Embedded
    private Editor editor;

    @ManyToOne
//    @Audited(targetAuditMode = NOT_AUDITED)
    private User modifier;

//    @Audited(targetAuditMode = NOT_AUDITED)
    @ManyToOne
    private Language language;

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

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

}
