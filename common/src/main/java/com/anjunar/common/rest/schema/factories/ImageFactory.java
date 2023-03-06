package com.anjunar.common.rest.schema.factories;

import com.anjunar.common.rest.schema.JsonContext;
import com.anjunar.introspector.bean.BeanProperty;
import com.google.common.reflect.TypeToken;
import com.anjunar.common.rest.api.MediaType;
import com.anjunar.common.rest.schema.schema.JsonImage;

public class ImageFactory extends JsonAbstractFactory<JsonImage> {

    @Override
    public boolean test(TypeToken<?> typeToken) {
        return typeToken.getRawType().equals(MediaType.class);
    }

    @Override
    public JsonImage build(TypeToken<?> typeToken, BeanProperty<?, ?> property, JsonContext context) {
        return new JsonImage();
    }

}
