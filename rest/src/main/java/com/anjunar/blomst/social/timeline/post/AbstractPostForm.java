package com.anjunar.blomst.social.timeline.post;

import com.anjunar.blomst.control.users.user.UserForm;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.blomst.shared.likeable.AbstractLikeableRestEntity;

import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ImagePostForm.class, name = "Image"),
        @JsonSubTypes.Type(value = LinkPostForm.class, name = "Link"),
        @JsonSubTypes.Type(value = TextPostForm.class, name = "Text"),
        @JsonSubTypes.Type(value = SystemPostForm.class, name = "System")}
)
@JsonSchema(widget = JsonNode.Widget.FORM)
public abstract class AbstractPostForm extends AbstractLikeableRestEntity {

    @JsonSchema(widget = JsonNode.Widget.TEXTAREA, title = "Text")
    private String text;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Owner", readOnly = true)
    private UserForm owner;

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Source", readOnly = true)
    private UUID source;

    public abstract <E> E accept(AbstractPostFormVisitor<E> visitor);

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserForm getOwner() {
        return owner;
    }

    public void setOwner(UserForm owner) {
        this.owner = owner;
    }

    public UUID getSource() {
        return source;
    }

    public void setSource(UUID source) {
        this.source = source;
    }

}
