package com.anjunar.blomst.social.timeline.post;

import com.anjunar.blomst.control.users.user.UserForm;
import com.anjunar.blomst.shared.users.user.IdentitySelect;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.common.rest.mapper.annotations.MapperView;
import com.anjunar.common.rest.mapper.annotations.MapperWrite;
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

    @JsonSchema(widget = JsonNode.Widget.TEXTAREA, title = "Text", naming = true)
    private String text;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Owner", readOnly = true)
    @MapperWrite
    private UserSelect owner;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Source", readOnly = true)
    @MapperWrite
    private IdentitySelect source;

    public abstract <E> E accept(AbstractPostFormVisitor<E> visitor);

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
