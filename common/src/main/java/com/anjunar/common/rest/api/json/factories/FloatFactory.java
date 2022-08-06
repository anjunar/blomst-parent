package com.anjunar.common.rest.api.json.factories;

import com.google.common.reflect.TypeToken;
import com.anjunar.common.rest.api.json.schema.JsonNumber;

@SuppressWarnings("UnstableApiUsage")
public class FloatFactory extends NumericFactory<JsonNumber> {
    @Override
    public boolean test(TypeToken<?> typeToken) {
        return typeToken.getRawType().equals(Float.class) || typeToken.getType().equals(float.class);
    }

    @Override
    public JsonNumber build(TypeToken<?> typeToken) {
        return new JsonNumber();
    }
}
