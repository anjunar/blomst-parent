package com.anjunar.blomst.social.timeline.post;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.anjunar.common.rest.api.json.annotations.JsonSchema;
import com.anjunar.common.rest.api.json.schema.JsonNode;
import com.anjunar.common.security.Identity;
import com.anjunar.blomst.social.timeline.AbstractPost;
import com.anjunar.blomst.shared.likeable.AbstractLikeableRestEntity;
import com.anjunar.blomst.shared.likeable.AbstractLikeableRestEntityConverter;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.common.security.IdentityProvider;

import jakarta.persistence.EntityManager;
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

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Owner")
    private UserSelect owner;

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Source", readOnly = true)
    private UUID source;

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

    public UUID getSource() {
        return source;
    }

    public void setSource(UUID source) {
        this.source = source;
    }

    public static class AbstractPostFormConverter<E extends AbstractPost, R extends AbstractPostForm> extends AbstractLikeableRestEntityConverter<E, R> {
        public R factory(R resource, E post) {
            resource.setId(post.getId());
            resource.setText(post.getText());
            resource.setOwner(UserSelect.factory(post.getOwner()));
            resource.setSource(post.getSource().getId());
            return super.factory(resource, post);
        }

        public E updater(R resource, E post, EntityManager entityManager, IdentityProvider identityProvider) {
            post.setOwner(identityProvider.getUser());
            post.setText(resource.getText());
            post.setSource(entityManager.find(Identity.class, resource.getSource()));
            return super.updater(resource, post, entityManager, identityProvider);
        }
    }

}
