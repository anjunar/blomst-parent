package com.anjunar.common.rest.api;

import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.fasterxml.jackson.annotation.JsonInclude;

public class Editor {

    @JsonSchema(widget = JsonNode.Widget.EDITOR, title = "HTML")
    @JsonInclude
    private String html = "";

    @JsonSchema(widget = JsonNode.Widget.TEXTAREA, title = "Text",naming = true)
    @JsonInclude
    private String text = "";

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
