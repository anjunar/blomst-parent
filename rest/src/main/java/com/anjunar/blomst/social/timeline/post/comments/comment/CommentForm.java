package com.anjunar.blomst.social.timeline.post.comments.comment;

import com.anjunar.blomst.control.users.user.UserForm;
import com.anjunar.blomst.shared.likeable.AbstractLikeableRestEntity;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
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
    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Owner", readOnly = true)
    private UserForm owner;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_MULTI_SELECT, title = "Likes")
    private final Set<UserForm> likes = new HashSet<>();

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
