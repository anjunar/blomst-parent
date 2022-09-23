package com.anjunar.blomst.social.chat;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.ddd.PostgresIndex;
import com.anjunar.common.i18n.Detector;
import com.anjunar.common.security.OwnerProvider;
import com.anjunar.common.security.User;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class ChatMessage extends AbstractEntity implements OwnerProvider {

    @PostgresIndex(type = PostgresIndex.Type.TEXT)
    @Column(columnDefinition="TEXT")
    private String text;

    private String language;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @ManyToMany(fetch = FetchType.LAZY)
    private final Set<User> participants = new HashSet<>();

    @PrePersist
    @PreUpdate
    private void prePersist() {
        language = Detector.detectLanguageOf(text);
    }

    public String getText() {
        return text;
    }

    public void setText(String message) {
        this.text = message;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<User> getParticipants() {
        return participants;
    }

}
