package com.anjunar.blomst.social.pages;

import com.anjunar.common.ddd.OracleIndex;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Editor {

    @Column(name = "html", columnDefinition = "CLOB")
    private String html;

    @OracleIndex(type = OracleIndex.Type.TEXT)
    @Column(name = "text", columnDefinition = "CLOB")
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
