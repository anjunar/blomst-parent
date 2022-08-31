package com.anjunar.blomst.social.pages;

import com.anjunar.common.ddd.OracleIndex;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;

@Embeddable
public class Editor {

    @Lob
    private String html;

    @OracleIndex(type = OracleIndex.Type.TEXT)
    @Lob
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
