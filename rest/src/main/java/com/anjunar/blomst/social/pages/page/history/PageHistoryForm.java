package com.anjunar.blomst.social.pages.page.history;

import com.anjunar.blomst.control.users.user.UserForm;
import com.anjunar.blomst.control.users.user.UserSelect;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.mapper.annotations.MapperProjection;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;

public class PageHistoryForm extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Title")
    private String title;

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Content")
    private String content;

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Text")
    private String text;

    @JsonSchema(widget = JsonNode.Widget.NUMBER, title = "Revision", id = true)
    private Number revision;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Modifier")
    @MapperProjection(UserSelect.class)
    private UserForm modifier;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Number getRevision() {
        return revision;
    }

    public void setRevision(Number revision) {
        this.revision = revision;
    }

    public UserForm getModifier() {
        return modifier;
    }

    public void setModifier(UserForm modifier) {
        this.modifier = modifier;
    }

}
