package com.anjunar.blomst.social.pages;

import com.anjunar.common.ddd.PostgresIndex;
import com.github.pemistahl.lingua.api.Language;
import com.github.pemistahl.lingua.api.LanguageDetector;
import com.github.pemistahl.lingua.api.LanguageDetectorBuilder;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Embeddable
public class Editor {

    private final static LanguageDetector detector = LanguageDetectorBuilder
            .fromAllLanguages()
            .build();

    @Column(columnDefinition="TEXT")
    private String html;

    @PostgresIndex(type = PostgresIndex.Type.TEXT)
    @Column(columnDefinition="TEXT")
    private String text;

    private String language;

    @PrePersist
    @PreUpdate
    private void prePersist() {
        Language detectedLanguage = detector.detectLanguageOf(text);
        language = detectedLanguage.name().toLowerCase();
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String content) {
        this.html = content;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
