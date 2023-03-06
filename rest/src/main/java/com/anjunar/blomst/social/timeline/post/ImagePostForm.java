package com.anjunar.blomst.social.timeline.post;

import com.anjunar.blomst.control.users.user.ImageConverter;
import com.anjunar.blomst.social.timeline.ImagePost;
import com.anjunar.common.rest.mapper.annotations.MapperConverter;
import com.anjunar.common.rest.mapper.annotations.MapperPolymorphism;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.rest.api.MediaType;

@MapperPolymorphism(ImagePost.class)
public class ImagePostForm extends AbstractPostForm {

    @JsonSchema(widget = JsonNode.Widget.IMAGE, title = "Picture")
    @MapperConverter(ImageConverter.class)
    private MediaType image = new MediaType();

    @Override
    public <E> E accept(AbstractPostFormVisitor<E> visitor) {
        return visitor.visit(this);
    }

    public MediaType getImage() {
        return image;
    }

    public void setImage(MediaType image) {
        this.image = image;
    }


}
