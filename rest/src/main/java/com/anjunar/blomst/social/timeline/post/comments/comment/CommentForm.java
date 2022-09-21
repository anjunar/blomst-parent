package com.anjunar.blomst.social.timeline.post.comments.comment;

import com.anjunar.blomst.shared.likeable.AbstractLikeableRestEntity;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.timeline.post.AbstractPostForm;
import com.anjunar.common.rest.mapper.annotations.MapperWrite;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class CommentForm extends AbstractLikeableRestEntity {

    @Size(max = 1000)
    @JsonSchema(widget = JsonNode.Widget.TEXTAREA, title = "Text", naming = true)
    private String text;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Post")
    @MapperWrite
    private AbstractPostForm post;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Parent Comment", readOnly = true, cycle = true)
    @MapperWrite
    private CommentForm parent;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Owner", readOnly = true)
    @MapperWrite
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

    public Set<UserSelect> getLikes() {
        return likes;
    }

}
