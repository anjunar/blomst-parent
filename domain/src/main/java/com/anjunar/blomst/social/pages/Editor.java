package com.anjunar.blomst.social.pages;

import com.anjunar.common.ddd.PostgresIndex;
import com.anjunar.common.i18n.Detector;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Embeddable
public class Editor {

    @Column(columnDefinition = "TEXT")
    private String html;

    @PostgresIndex(type = PostgresIndex.Type.TEXT)
    @Column(columnDefinition = "TEXT")
    private String text;

    private String language;

    @PrePersist
    @PreUpdate
    private void prePersist() {
        String detectedLanguage = Detector.detectLanguageOf(text);
        language = detectedLanguage.toLowerCase();
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
