package com.anjunar.blomst.social.timeline.post;

import com.anjunar.blomst.shared.users.user.IdentitySelect;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.common.rest.api.Editor;
import com.anjunar.common.validators.Dom;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.blomst.shared.likes.likeable.AbstractLikeableRestEntity;
import jakarta.validation.constraints.NotNull;

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

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Source", readOnly = true)
    private IdentitySelect source;

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

    public IdentitySelect getSource() {
        return source;
    }

    public void setSource(IdentitySelect source) {
        this.source = source;
    }

}
