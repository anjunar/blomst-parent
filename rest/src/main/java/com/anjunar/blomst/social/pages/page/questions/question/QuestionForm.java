package com.anjunar.blomst.social.pages.page.questions.question;

import com.anjunar.blomst.shared.likes.likeable.AbstractLikeableRestEntity;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.pages.page.PageForm;
import com.anjunar.blomst.social.pages.page.PageReference;
import com.anjunar.common.rest.api.Editor;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.validators.Dom;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class QuestionForm extends AbstractLikeableRestEntity {

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.REFERENCE, title = "Page", readOnly = true)
    private PageReference page;

    @Size(min = 3, max = 80)
    @NotBlank
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Question", naming = true)
    private String topic;

    @JsonSchema(widget = JsonNode.Widget.EDITOR, title = "Editor")
    @Dom
    private Editor editor = new Editor();

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Owner", readOnly = true)
    private UserSelect owner;

    public PageReference getPage() {
        return page;
    }

    public void setPage(PageReference page) {
        this.page = page;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    public UserSelect getOwner() {
        return owner;
    }

    public void setOwner(UserSelect owner) {
        this.owner = owner;
    }

}
