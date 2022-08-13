package com.anjunar.common.rest.schema.factories;

import com.google.common.reflect.TypeToken;
import com.anjunar.common.rest.api.ImageType;
import com.anjunar.common.rest.schema.schema.JsonImage;

public class ImageFactory extends JsonAbstractFactory<JsonImage> {

    @Override
    public boolean test(TypeToken<?> typeToken) {
        return typeToken.getRawType().equals(ImageType.class);
    }

    @Override
    public JsonImage build(TypeToken<?> typeToken) {
        return new JsonImage();
    }

}
