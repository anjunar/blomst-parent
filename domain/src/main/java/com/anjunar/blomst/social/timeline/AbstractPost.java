package com.anjunar.blomst.social.timeline;

import com.anjunar.common.ddd.PostgresIndex;
import com.anjunar.common.security.Identity;
import com.anjunar.common.security.User;
import com.anjunar.blomst.shared.Likeable;

import com.github.pemistahl.lingua.api.Language;
import com.github.pemistahl.lingua.api.LanguageDetector;
import com.github.pemistahl.lingua.api.LanguageDetectorBuilder;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class AbstractPost extends Likeable {

    private final static LanguageDetector detector = LanguageDetectorBuilder
            .fromAllLanguages()
            .build();

    @PostgresIndex(type = PostgresIndex.Type.TEXT)
    @Column(columnDefinition="TEXT")
    private String text;

    private String language;

    @ManyToOne
    private User owner;

    @ManyToOne
    private Identity source;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private final List<Comment> comments = new ArrayList<>();

    @PrePersist
    @PreUpdate
    private void prePersist() {
        Language detectedLanguage = detector.detectLanguageOf(text);
        language = detectedLanguage.name().toLowerCase();
    }

    public abstract <E> E accept(AbstractPostVisitor<E> visitor);

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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Identity getSource() {
        return source;
    }

    public void setSource(Identity source) {
        this.source = source;
    }

    public List<Comment> getComments() {
        return comments;
    }

}
