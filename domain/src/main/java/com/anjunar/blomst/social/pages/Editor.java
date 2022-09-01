package com.anjunar.blomst.social.pages;

import com.anjunar.common.ddd.PostgresIndex;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Editor {

    @Column(columnDefinition="TEXT")
    private String html;

    @PostgresIndex(type = PostgresIndex.Type.TEXT)
    @Column(columnDefinition="TEXT")
    private String text;

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
}
