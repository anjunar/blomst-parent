package com.anjunar.blomst.social.timeline.post.comments.comment;

import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.blomst.social.timeline.AbstractPost;
import com.anjunar.blomst.social.timeline.Comment;
import com.anjunar.blomst.shared.likeable.AbstractLikeableRestEntity;
import com.anjunar.blomst.shared.likeable.AbstractLikeableRestEntityConverter;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.common.security.IdentityProvider;

import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class CommentForm extends AbstractLikeableRestEntity {

    @Size(max = 1000)
    @JsonSchema(widget = JsonNode.Widget.TEXTAREA, title = "Text")
    private String text;

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Post Id")
    private UUID post;

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Parent Comment Id")
    private UUID parent;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Owner")
    private UserSelect owner;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_MULTI_SELECT, title = "Likes")
    private final Set<UserSelect> likes = new HashSet<>();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UUID getPost() {
        return post;
    }

    public void setPost(UUID post) {
        this.post = post;
    }

    public UUID getParent() {
        return parent;
    }

    public void setParent(UUID parent) {
        this.parent = parent;
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

    private static class CommentFormConverter extends AbstractLikeableRestEntityConverter<Comment, CommentForm> {

        public static CommentFormConverter INSTANCE = new CommentFormConverter();

        public CommentForm factory(CommentForm resource, Comment comment) {
            resource.setId(comment.getId());
            resource.setText(comment.getText());
            resource.setPost(comment.getPost().getId());
            if (comment.getParent() != null) {
                resource.setParent(comment.getParent().getId());
            }
            resource.setOwner(UserSelect.factory(comment.getOwner()));
            return super.factory(resource, comment);
        }

        public Comment updater(CommentForm resource, Comment comment, EntityManager entityManager, IdentityProvider identityProvider) {
            comment.setOwner(identityProvider.getUser());
            comment.setPost(entityManager.find(AbstractPost.class, resource.getPost()));
            if (resource.getParent() != null) {
                comment.setParent(entityManager.find(Comment.class, resource.getParent()));
            }
            comment.setText(resource.getText());
            return super.updater(resource, comment, entityManager, identityProvider);
        }
    }

    public static CommentForm factory(Comment comment) {
        return CommentFormConverter.INSTANCE.factory(new CommentForm(), comment);
    }

    public static Comment updater(CommentForm resource, Comment comment, IdentityProvider identityProvider, EntityManager entityManager) {
        return CommentFormConverter.INSTANCE.updater(resource, comment, entityManager, identityProvider);
    }
}
