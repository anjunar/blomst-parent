package com.anjunar.blomst.social.pages.page.history;

import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.blomst.social.pages.Page;
import com.anjunar.blomst.shared.users.user.UserSelect;

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
    private UserSelect modifier;

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

    public UserSelect getModifier() {
        return modifier;
    }

    public void setModifier(UserSelect modifier) {
        this.modifier = modifier;
    }

    public static PageHistoryForm factory(Page page, Number revision) {
        PageHistoryForm resource = new PageHistoryForm();
        resource.setId(page.getId());
        resource.setTitle(page.getTitle());
        resource.setContent(page.getContent());
        resource.setText(page.getText());
        resource.setRevision(revision);
        resource.setModified(page.getModified());
        resource.setCreated(page.getCreated());
        resource.setModifier(UserSelect.factory(page.getModifier()));
        return resource;
    }
}
