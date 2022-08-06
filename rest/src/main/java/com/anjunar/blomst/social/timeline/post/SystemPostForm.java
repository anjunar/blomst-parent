package com.anjunar.blomst.social.timeline.post;

import com.anjunar.common.rest.api.json.annotations.JsonSchema;
import com.anjunar.common.rest.api.json.schema.JsonNode;
import com.anjunar.blomst.social.timeline.SystemPost;
import com.anjunar.common.security.IdentityProvider;

import javax.persistence.EntityManager;

public class SystemPostForm extends AbstractPostForm{

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Hash Link")
    private String hash;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public <E> E accept(AbstractPostFormVisitor<E> visitor) {
        return visitor.visit(this);
    }

    private static class SystemPostFormConverter extends AbstractPostFormConverter<SystemPost, SystemPostForm> {

        public static SystemPostFormConverter INSTANCE = new SystemPostFormConverter();

        public SystemPostForm factory(SystemPostForm form, SystemPost post) {
            form.setHash(post.getHash());
            return super.factory(form, post);
        }

        public SystemPost updater(SystemPostForm resource, SystemPost post, IdentityProvider identityProvider, EntityManager entityManager) {
            post.setHash(resource.getHash());
            return super.updater(resource, post, entityManager, identityProvider);
        }
    }

    public static SystemPostForm factory(SystemPost post) {
        return SystemPostFormConverter.INSTANCE.factory(new SystemPostForm(), post);
    }

    public static SystemPost updater(SystemPostForm resource, SystemPost post, IdentityProvider identityProvider, EntityManager entityManager) {
        return SystemPostFormConverter.INSTANCE.updater(resource, post, entityManager, identityProvider);
    }

}
