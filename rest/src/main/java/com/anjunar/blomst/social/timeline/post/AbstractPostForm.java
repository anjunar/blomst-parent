package com.anjunar.blomst.social.timeline.post;

import com.anjunar.blomst.shared.users.user.IdentityReference;
import com.anjunar.blomst.shared.users.user.IdentitySelect;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.timeline.post.comments.comment.CommentForm;
import com.anjunar.common.rest.api.Editor;
import com.anjunar.common.validators.Dom;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.blomst.shared.likes.likeable.AbstractLikeableRestEntity;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ImagePostForm.class, name = "ImagePost"),
        @JsonSubTypes.Type(value = VideoPostForm.class, name = "VideoPost"),
        @JsonSubTypes.Type(value = TextPostForm.class, name = "TextPost")}
)
@JsonSchema(widget = JsonNode.Widget.FORM)
public abstract class AbstractPostForm extends AbstractLikeableRestEntity {

    @JsonSchema(widget = JsonNode.Widget.EDITOR, title = "Editor")
    @Dom
    @NotNull
    private Editor editor = new Editor();

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Owner", readOnly = true)
    private UserSelect owner;

    @JsonSchema(widget = JsonNode.Widget.REFERENCE, title = "Source", readOnly = true)
    private IdentityReference source;

    @JsonSchema(widget = JsonNode.Widget.REPEAT, title = "Comments")
    private List<CommentForm> comments = new ArrayList<>();

    public abstract <E> E accept(AbstractPostFormVisitor<E> visitor);

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

    public IdentityReference getSource() {
        return source;
    }

    public void setSource(IdentityReference source) {
        this.source = source;
    }

    public List<CommentForm> getComments() {
        return comments;
    }

    public void setComments(List<CommentForm> comments) {
        this.comments = comments;
    }
}
