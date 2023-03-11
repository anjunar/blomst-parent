package com.anjunar.blomst.social.pages.page.questions.question.answers.answer;

import com.anjunar.blomst.shared.likes.likeable.AbstractLikeableRestEntity;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.pages.page.questions.question.QuestionForm;
import com.anjunar.blomst.social.pages.page.questions.question.QuestionReference;
import com.anjunar.common.rest.api.Editor;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.validators.Dom;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotNull;

@JsonSchema(widget = JsonNode.Widget.FORM)
@JsonTypeName("Answer")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true)
public class AnswerForm extends AbstractLikeableRestEntity {

    @JsonSchema(widget = JsonNode.Widget.EDITOR, title = "Editor")
    @Dom
    private Editor editor;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.REFERENCE, title = "Question", readOnly = true)
    private QuestionReference question;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Owner", readOnly = true)
    private UserSelect owner;

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    public QuestionReference getQuestion() {
        return question;
    }

    public void setQuestion(QuestionReference question) {
        this.question = question;
    }

    public UserSelect getOwner() {
        return owner;
    }

    public void setOwner(UserSelect owner) {
        this.owner = owner;
    }


}
