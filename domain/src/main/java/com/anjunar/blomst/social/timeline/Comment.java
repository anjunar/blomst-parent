package com.anjunar.blomst.social.timeline;

import com.anjunar.common.ddd.PostgresIndex;
import com.anjunar.common.security.User;
import com.anjunar.blomst.shared.Likeable;
import com.github.pemistahl.lingua.api.Language;
import com.github.pemistahl.lingua.api.LanguageDetector;
import com.github.pemistahl.lingua.api.LanguageDetectorBuilder;
import org.hibernate.annotations.Filter;

import jakarta.persistence.*;

@Entity
@Filter(name = "deletedFilter", condition = "deleted = false")
public class Comment extends Likeable {

    private final static LanguageDetector detector = LanguageDetectorBuilder
            .fromAllLanguages()
            .build();

    @PostgresIndex(type = PostgresIndex.Type.TEXT)
    @Column(columnDefinition="TEXT")
    private String text;

    private String language;

    @ManyToOne(fetch = FetchType.LAZY)
    private AbstractPost post;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @ManyToOne
    private Comment parent;

    @PrePersist
    @PreUpdate
    private void prePersist() {
        Language detectedLanguage = detector.detectLanguageOf(text);
        language = detectedLanguage.name().toLowerCase();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public AbstractPost getPost() {
        return post;
    }

    public void setPost(AbstractPost post) {
        this.post = post;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }
}
