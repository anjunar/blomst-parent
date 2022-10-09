package com.anjunar.blomst.social.timeline;

import com.anjunar.blomst.shared.Likeable;
import com.anjunar.common.ddd.PostgresIndex;
import com.anjunar.common.i18n.Detector;
import com.anjunar.common.security.User;
import jakarta.persistence.*;
import org.hibernate.annotations.Filter;

@Entity
@Filter(name = "deletedFilter", condition = "deleted = false")
public class Comment extends Likeable {

    @PostgresIndex(type = PostgresIndex.Type.TEXT)
    @Column(columnDefinition = "TEXT")
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
        String detectedLanguage = Detector.detectLanguageOf(text);
        language = detectedLanguage.toLowerCase();
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
