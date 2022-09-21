package com.anjunar.common.rest.schema.factories;

import com.anjunar.common.rest.schema.JsonContext;
import com.google.common.reflect.TypeToken;
import com.anjunar.common.rest.schema.schema.JsonString;
import com.anjunar.introspector.bean.BeanProperty;

import java.util.UUID;

@SuppressWarnings("UnstableApiUsage")
public class UUIDFactory extends JsonAbstractFactory<JsonString> {
    @Override
    public boolean test(TypeToken<?> typeToken) {
        return typeToken.getRawType().equals(UUID.class);
    }

    @Override
    public JsonString build(TypeToken<?> typeToken, BeanProperty<?, ?> property, JsonContext context) {
        JsonString jsonString = new JsonString();
        jsonString.setFormat(JsonString.Format.UUID);
        return jsonString;
    }

    @Override
    public JsonString buildWithAnnotation(BeanProperty<?, ?> property, JsonContext context) {
        JsonString jsonString = super.buildWithAnnotation(property, context);
        return jsonString;
    }
}
