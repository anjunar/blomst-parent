package com.anjunar.blomst.social.pages.page.questions.question.answers.answer;

import com.anjunar.common.rest.api.Editor;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.validators.Dom;
import com.anjunar.blomst.shared.likeable.AbstractLikeableRestEntity;
import com.anjunar.blomst.shared.users.user.UserSelect;

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
    private UserSelect owner;

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

    public UserSelect getOwner() {
        return owner;
    }

    public void setOwner(UserSelect owner) {
        this.owner = owner;
    }


}
