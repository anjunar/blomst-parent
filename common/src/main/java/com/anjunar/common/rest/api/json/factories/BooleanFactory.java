package com.anjunar.common.rest.api.json.factories;

import com.google.common.reflect.TypeToken;
import com.anjunar.common.rest.api.json.schema.JsonBoolean;
import de.anjunar.introspector.bean.BeanProperty;

@SuppressWarnings("UnstableApiUsage")
public class BooleanFactory extends JsonAbstractFactory<JsonBoolean> {
    @Override
    public boolean test(TypeToken<?> typeToken) {
        return typeToken.getRawType().equals(Boolean.class) || typeToken.getType().equals(boolean.class);
    }

    @Override
    public JsonBoolean build(TypeToken<?> typeToken) {
        return new JsonBoolean();
    }

    @Override
    public JsonBoolean buildWithAnnotation(BeanProperty<?, ?> property) {
        JsonBoolean jsonBoolean = super.buildWithAnnotation(property);
        return jsonBoolean;
    }
}

