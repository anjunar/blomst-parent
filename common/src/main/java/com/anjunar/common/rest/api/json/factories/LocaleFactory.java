package com.anjunar.common.rest.api.json.factories;

import com.google.common.reflect.TypeToken;
import com.anjunar.common.rest.api.json.schema.JsonString;
import com.anjunar.introspector.bean.BeanProperty;

import java.util.Locale;

@SuppressWarnings("UnstableApiUsage")
public class LocaleFactory extends JsonAbstractFactory<JsonString> {
    @Override
    public boolean test(TypeToken<?> typeToken) {
        return typeToken.getRawType().equals(Locale.class);
    }

    @Override
    public JsonString build(TypeToken<?> typeToken) {
        return new JsonString();
    }

    @Override
    public JsonString buildWithAnnotation(BeanProperty<?, ?> property) {
        JsonString jsonString = super.buildWithAnnotation(property);
        return jsonString;
    }
}
