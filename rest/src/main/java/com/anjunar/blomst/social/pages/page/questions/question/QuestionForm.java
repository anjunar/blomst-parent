package com.anjunar.blomst.social.pages.page.questions.question;

import com.anjunar.common.rest.api.Editor;
import com.anjunar.common.rest.api.json.annotations.JsonSchema;
import com.anjunar.common.rest.api.json.schema.JsonNode;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.User;
import com.anjunar.common.validators.Dom;
import com.anjunar.blomst.social.pages.Page;
import com.anjunar.blomst.social.pages.page.Question;
import com.anjunar.blomst.shared.likeable.AbstractLikeableRestEntity;
import com.anjunar.blomst.shared.likeable.AbstractLikeableRestEntityConverter;
import com.anjunar.blomst.shared.users.user.UserSelect;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    private UserSelect owner;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_MULTI_SELECT, title = "Likes")
    private Set<UserSelect> likes = new HashSet<>();

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

    public UserSelect getOwner() {
        return owner;
    }

    public void setOwner(UserSelect owner) {
        this.owner = owner;
    }

    public Set<UserSelect> getLikes() {
        return likes;
    }

    private static class QuestionFormConverter extends AbstractLikeableRestEntityConverter<Question, QuestionForm> {

        public static QuestionFormConverter INSTANCE = new QuestionFormConverter();

        public QuestionForm factory(QuestionForm resource, Question question) {
            resource.setId(question.getId());
            resource.setTopic(question.getTopic());
            resource.setEditor(Editor.factory(question.getHtml(), question.getText()));
            resource.setPage(question.getPage().getId());
            resource.setOwner(UserSelect.factory(question.getOwner()));
            return super.factory(resource, question);
        }

        public Question updater(QuestionForm resource, Question question, EntityManager entityManager, IdentityProvider identityProvider) {
            question.setTopic(resource.getTopic());
            question.setPage(entityManager.find(Page.class, resource.getPage()));
            question.setHtml(resource.getEditor().getHtml());
            question.setText(resource.getEditor().getText());
            User owner = entityManager.find(User.class, resource.getOwner().getId());
            question.setOwner(owner);
            return super.updater(resource, question, entityManager, identityProvider);
        }
    }

    public static QuestionForm factory(Question question) {
        return QuestionFormConverter.INSTANCE.factory(new QuestionForm(), question);
    }

    public static Question updater(QuestionForm resource, Question question, IdentityProvider identityProvider, EntityManager entityManager) {
        return QuestionFormConverter.INSTANCE.updater(resource, question, entityManager, identityProvider);
    }
}
