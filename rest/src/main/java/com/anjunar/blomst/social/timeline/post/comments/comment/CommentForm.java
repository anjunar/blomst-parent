package com.anjunar.blomst.social.timeline.post.comments.comment;

import com.anjunar.blomst.shared.likes.likeable.AbstractLikeableRestEntity;
import com.anjunar.blomst.shared.likes.likeable.LikesConverter;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.timeline.post.AbstractPostForm;
import com.anjunar.common.rest.mapper.annotations.MapperConverter;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JsonSchema(widget = JsonNode.Widget.FORM)
@JsonTypeName("Comment")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true)
public class CommentForm extends AbstractLikeableRestEntity {

    @Size(max = 1000)
    @JsonSchema(widget = JsonNode.Widget.TEXTAREA, title = "Text", naming = true)
    private String text;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Post")
    private AbstractPostForm post;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Parent Comment", readOnly = true, cycle = true)
    private CommentForm parent;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Owner", readOnly = true)
    private UserSelect owner;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LIKE, title = "Likes")
    @MapperConverter(LikesConverter.class)
    private boolean likes = false;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public AbstractPostForm getPost() {
        return post;
    }

    public void setPost(AbstractPostForm post) {
        this.post = post;
    }

    public CommentForm getParent() {
        return parent;
    }

    public void setParent(CommentForm parent) {
        this.parent = parent;
    }

    public UserSelect getOwner() {
        return owner;
    }

    public void setOwner(UserSelect owner) {
        this.owner = owner;
    }

    @Override
    public boolean isLikes() {
        return likes;
    }

    @Override
    public void setLikes(boolean likes) {
        this.likes = likes;
    }
}
