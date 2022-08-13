package com.anjunar.blomst.social.pages.page.questions.question.answers.answer;

import com.anjunar.common.rest.api.Editor;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.User;
import com.anjunar.common.validators.Dom;
import com.anjunar.blomst.social.pages.page.Answer;
import com.anjunar.blomst.social.pages.page.Question;
import com.anjunar.blomst.shared.likeable.AbstractLikeableRestEntity;
import com.anjunar.blomst.shared.likeable.AbstractLikeableRestEntityConverter;
import com.anjunar.blomst.shared.users.user.UserSelect;

import jakarta.persistence.EntityManager;
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

    private static class AnswerFormConverter extends AbstractLikeableRestEntityConverter<Answer, AnswerForm> {

        public static AnswerFormConverter INSTANCE = new AnswerFormConverter();

        public AnswerForm factory(AnswerForm resource, Answer answer) {
            resource.setId(answer.getId());
            resource.setEditor(Editor.factory(answer.getHtml(), answer.getText()));
            resource.setTopic(answer.getTopic().getId());
            resource.setCreated(answer.getCreated());
            resource.setOwner(UserSelect.factory(answer.getOwner()));
            return super.factory(resource, answer);
        }

        @Override
        public Answer updater(AnswerForm form, Answer answer, EntityManager entityManager, IdentityProvider identityProvider) {
            Question question = entityManager.find(Question.class, form.getTopic());
            answer.setText(form.getEditor().getText());
            answer.setHtml(form.getEditor().getHtml());
            answer.setTopic(question);
            User owner = entityManager.find(User.class, form.getOwner().getId());
            answer.setOwner(owner);
            return super.updater(form, answer, entityManager, identityProvider);
        }

    }

    public static AnswerForm factory(Answer answer) {
        return AnswerFormConverter.INSTANCE.factory(new AnswerForm(), answer);
    }

    public static void update(Answer answer, AnswerForm form, IdentityProvider identityProvider, EntityManager entityManager) {
        AnswerFormConverter.INSTANCE.updater(form, answer, entityManager, identityProvider);
    }

}
