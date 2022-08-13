package com.anjunar.common.rest.schema.factories;

import com.google.common.reflect.TypeToken;
import com.anjunar.common.rest.schema.schema.JsonInteger;

@SuppressWarnings("UnstableApiUsage")
public class IntegerFactory extends NumericFactory<JsonInteger> {
    @Override
    public boolean test(TypeToken<?> typeToken) {
        return typeToken.getRawType().equals(Integer.class) || typeToken.getType().equals(int.class);
    }

    @Override
    public JsonInteger build(TypeToken<?> typeToken) {
        return new JsonInteger();
    }
}
