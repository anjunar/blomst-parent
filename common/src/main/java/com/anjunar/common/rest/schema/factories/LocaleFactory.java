package com.anjunar.common.rest.schema.factories;

import com.anjunar.common.rest.schema.JsonContext;
import com.google.common.reflect.TypeToken;
import com.anjunar.common.rest.schema.schema.JsonString;
import com.anjunar.introspector.bean.BeanProperty;

import java.util.Locale;

@SuppressWarnings("UnstableApiUsage")
public class LocaleFactory extends JsonAbstractFactory<JsonString> {
    @Override
    public boolean test(TypeToken<?> typeToken) {
        return typeToken.getRawType().equals(Locale.class);
    }

    @Override
    public JsonString build(TypeToken<?> typeToken, BeanProperty<?, ?> property, JsonContext context) {
        return new JsonString();
    }

    @Override
    public JsonString buildWithAnnotation(BeanProperty<?, ?> property, JsonContext context) {
        JsonString jsonString = super.buildWithAnnotation(property, context);
        return jsonString;
    }
}
