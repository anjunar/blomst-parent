package com.anjunar.blomst.social.pages.page.questions.question.answers.answer;

import com.anjunar.blomst.control.users.user.UserForm;
import com.anjunar.blomst.control.users.user.UserSelect;
import com.anjunar.blomst.shared.likeable.AbstractLikeableRestEntity;
import com.anjunar.common.rest.api.Editor;
import com.anjunar.common.rest.mapper.annotations.MapperProjection;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.validators.Dom;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class AnswerForm extends AbstractLikeableRestEntity {

    @JsonSchema(widget = JsonNode.Widget.EDITOR, title = "Editor")
    @Dom
    private Editor editor;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Topic Id")
    private UUID topic;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Owner")
    @MapperProjection(UserSelect.class)
    private UserForm owner;

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    public UUID getTopic() {
        return topic;
    }

    public void setTopic(UUID topic) {
        this.topic = topic;
    }

    public UserForm getOwner() {
        return owner;
    }

    public void setOwner(UserForm owner) {
        this.owner = owner;
    }


}
