package com.anjunar.common.rest.schema.factories;

import com.anjunar.common.rest.schema.JsonContext;
import com.anjunar.introspector.bean.BeanProperty;
import com.google.common.reflect.TypeToken;
import com.anjunar.common.rest.schema.schema.JsonInteger;

@SuppressWarnings("UnstableApiUsage")
public class LongFactory extends NumericFactory<JsonInteger> {
    @Override
    public boolean test(TypeToken<?> typeToken) {
        return typeToken.getRawType().equals(Long.class) || typeToken.getType().equals(long.class);
    }

    @Override
    public JsonInteger build(TypeToken<?> typeToken, BeanProperty<?, ?> property, JsonContext context) {
        return new JsonInteger();
    }

}
