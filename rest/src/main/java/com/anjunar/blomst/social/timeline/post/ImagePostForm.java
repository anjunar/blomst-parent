package com.anjunar.blomst.social.timeline.post;

import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.rest.api.ImageType;

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


}
