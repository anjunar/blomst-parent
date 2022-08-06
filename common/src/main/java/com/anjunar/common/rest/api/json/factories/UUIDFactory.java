package com.anjunar.common.rest.api.json.factories;

import com.google.common.reflect.TypeToken;
import com.anjunar.common.rest.api.json.schema.JsonString;
import de.anjunar.introspector.bean.BeanProperty;

import java.util.UUID;

@SuppressWarnings("UnstableApiUsage")
public class UUIDFactory extends JsonAbstractFactory<JsonString> {
    @Override
    public boolean test(TypeToken<?> typeToken) {
        return typeToken.getRawType().equals(UUID.class);
    }

    @Override
    public JsonString build(TypeToken<?> typeToken) {
        JsonString jsonString = new JsonString();
        jsonString.setFormat(JsonString.Format.UUID);
        return jsonString;
    }

    @Override
    public JsonString buildWithAnnotation(BeanProperty<?, ?> property) {
        JsonString jsonString = super.buildWithAnnotation(property);
        return jsonString;
    }
}
