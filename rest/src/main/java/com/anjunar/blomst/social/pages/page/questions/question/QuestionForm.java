package com.anjunar.blomst.social.pages.page.questions.question;

import com.anjunar.blomst.control.users.user.UserForm;
import com.anjunar.blomst.shared.likeable.AbstractLikeableRestEntity;
import com.anjunar.common.rest.api.Editor;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.validators.Dom;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class QuestionForm extends AbstractLikeableRestEntity {

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Page Id")
    private UUID page;

    @Size(min = 3, max = 80)
    @NotBlank
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Question")
    private String topic;

    @JsonSchema(widget = JsonNode.Widget.EDITOR, title = "Editor")
    @Dom
    private Editor editor = new Editor();

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Owner")
    private UserForm owner;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_MULTI_SELECT, title = "Likes")
    private final Set<UserForm> likes = new HashSet<>();

    public UUID getPage() {
        return page;
    }

    public void setPage(UUID page) {
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

    public UserForm getOwner() {
        return owner;
    }

    public void setOwner(UserForm owner) {
        this.owner = owner;
    }

    public Set<UserForm> getLikes() {
        return likes;
    }

}
