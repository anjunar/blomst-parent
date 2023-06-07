package com.anjunar.blomst.social.timeline.post.comments.comment;

import com.anjunar.blomst.shared.likes.likeable.AbstractLikeableRestEntity;
import com.anjunar.blomst.shared.likes.likeable.LikesConverter;
import com.anjunar.blomst.shared.users.user.UserReference;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.timeline.post.AbstractPostReference;
import com.anjunar.common.rest.mapper.annotations.MapperConverter;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(required = true)
    private String text;

    @JsonSchema(widget = JsonNode.Widget.REFERENCE, title = "Post", readOnly = true)
    private AbstractPostReference post;

    @JsonSchema(widget = JsonNode.Widget.REFERENCE, title = "Parent Comment", readOnly = true)
    
    private CommentReference parent;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Owner", readOnly = true)
    @JsonProperty(required = true)
    private UserSelect owner;

    @JsonSchema(widget = JsonNode.Widget.CHECKBOX, title = "Empty", readOnly = true)
    private boolean empty;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public AbstractPostReference getPost() {
        return post;
    }

    public void setPost(AbstractPostReference post) {
        this.post = post;
    }

    public CommentReference getParent() {
        return parent;
    }

    public void setParent(CommentReference parent) {
        this.parent = parent;
    }

    public UserSelect getOwner() {
        return owner;
    }

    public void setOwner(UserSelect owner) {
        this.owner = owner;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
