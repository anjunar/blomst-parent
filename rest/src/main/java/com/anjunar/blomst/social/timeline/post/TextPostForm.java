package com.anjunar.blomst.social.timeline.post;

import com.anjunar.blomst.social.timeline.TextPost;
import com.anjunar.common.security.IdentityProvider;

import javax.persistence.EntityManager;

public class TextPostForm extends AbstractPostForm {

    @Override
    public <E> E accept(AbstractPostFormVisitor<E> visitor) {
        return visitor.visit(this);
    }

    private static class TextPostFormConverter extends AbstractPostFormConverter<TextPost, TextPostForm> {

        public static TextPostFormConverter INSTANCE = new TextPostFormConverter();

        public TextPostForm factory(TextPostForm form, TextPost post) {
            return super.factory(form, post);
        }

        public TextPost updater(TextPostForm resource, TextPost post, IdentityProvider identityProvider, EntityManager entityManager) {
            return super.updater(resource, post, entityManager, identityProvider);
        }
    }

    public static TextPostForm factory(TextPost post) {
        return TextPostFormConverter.INSTANCE.factory(new TextPostForm(), post);
    }

    public static TextPost updater(TextPostForm resource, TextPost post, IdentityProvider identityProvider, EntityManager entityManager) {
        return TextPostFormConverter.INSTANCE.updater(resource, post, entityManager, identityProvider);
    }

}
