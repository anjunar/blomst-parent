package com.anjunar.common.rest.api;

import com.anjunar.common.rest.api.json.annotations.JsonSchema;
import com.anjunar.common.rest.api.json.schema.JsonNode;

public class Editor {

    @JsonSchema(widget = JsonNode.Widget.EDITOR, title = "HTML")
    private String html;

    @JsonSchema(widget = JsonNode.Widget.TEXTAREA, title = "Text",naming = true)
    private String text;

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

    public static Editor factory(String content, String text) {
        Editor editor = new Editor();
        editor.setHtml(content);
        editor.setText(text);
        return editor;
    }

}
