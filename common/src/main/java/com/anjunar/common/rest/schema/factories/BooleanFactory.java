package com.anjunar.common.rest.schema.factories;

import com.anjunar.common.rest.schema.JsonContext;
import com.google.common.reflect.TypeToken;
import com.anjunar.common.rest.schema.schema.JsonBoolean;
import com.anjunar.introspector.bean.BeanProperty;

@SuppressWarnings("UnstableApiUsage")
public class BooleanFactory extends JsonAbstractFactory<JsonBoolean> {
    @Override
    public boolean test(TypeToken<?> typeToken) {
        return typeToken.getRawType().equals(Boolean.class) || typeToken.getType().equals(boolean.class);
    }

    @Override
    public JsonBoolean build(TypeToken<?> typeToken, BeanProperty<?, ?> property, JsonContext context) {
        return new JsonBoolean();
    }

    @Override
    public JsonBoolean buildWithAnnotation(BeanProperty<?, ?> property, JsonContext context) {
        JsonBoolean jsonBoolean = super.buildWithAnnotation(property, context);
        return jsonBoolean;
    }
}

