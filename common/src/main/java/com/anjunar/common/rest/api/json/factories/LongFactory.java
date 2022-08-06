package com.anjunar.common.rest.api.json.factories;

import com.google.common.reflect.TypeToken;
import com.anjunar.common.rest.api.json.schema.JsonInteger;

@SuppressWarnings("UnstableApiUsage")
public class LongFactory extends NumericFactory<JsonInteger> {
    @Override
    public boolean test(TypeToken<?> typeToken) {
        return typeToken.getRawType().equals(Long.class) || typeToken.getType().equals(long.class);
    }

    @Override
    public JsonInteger build(TypeToken<?> typeToken) {
        return new JsonInteger();
    }

}
