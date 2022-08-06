package com.anjunar.common.rest.api.json.factories;

import com.google.common.reflect.TypeToken;
import com.anjunar.common.rest.api.json.schema.JsonNumber;

@SuppressWarnings("UnstableApiUsage")
public class NumberFactory<N extends Number> extends NumericFactory<JsonNumber> {
    @Override
    public boolean test(TypeToken<?> typeToken) {
        return typeToken.getRawType().equals(Number.class);
    }

    @Override
    public JsonNumber build(TypeToken<?> typeToken) {
        return new JsonNumber();
    }
}
