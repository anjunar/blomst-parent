package com.anjunar.blomst.social.timeline.post;

import com.anjunar.common.filedisk.Image;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.blomst.social.timeline.ImagePost;
import com.anjunar.common.rest.api.ImageType;
import com.anjunar.common.security.IdentityProvider;

import jakarta.persistence.EntityManager;

public class ImagePostForm extends AbstractPostForm {

    @JsonSchema(widget = JsonNode.Widget.IMAGE, title = "Picture")
    private ImageType image = new ImageType();

    @Override
    public <E> E accept(AbstractPostFormVisitor<E> visitor) {
        return visitor.visit(this);
    }

    public ImageType getImage() {
        return image;
    }

    public void setImage(ImageType image) {
        this.image = image;
    }

    public static class ImagePostFormConverter extends AbstractPostFormConverter<ImagePost, ImagePostForm> {

        public static ImagePostFormConverter INSTANCE = new ImagePostFormConverter();

        public ImagePostForm factory(ImagePostForm post, ImagePost form) {
            post.setImage(ImageType.factory(form.getImage()));

            return super.factory(post, form);
        }

        public ImagePost updater(ImagePostForm resource, ImagePost post, EntityManager entityManager, IdentityProvider identityProvider) {
            if (post.getImage() == null) {
                post.setImage(ImageType.updater(resource.getImage(), new Image()));
            } else {
                post.setImage(ImageType.updater(resource.getImage(), post.getImage()));
            }

            return super.updater(resource, post, entityManager, identityProvider);
        }
    }

    public static ImagePostForm factory(ImagePost form) {
        return new ImagePostFormConverter().factory(new ImagePostForm(), form);
    }

    public static ImagePost updater(ImagePostForm resource, ImagePost post, IdentityProvider identityProvider, EntityManager entityManager) {
        return ImagePostFormConverter.INSTANCE.updater(resource, post, entityManager, identityProvider);
    }

}
