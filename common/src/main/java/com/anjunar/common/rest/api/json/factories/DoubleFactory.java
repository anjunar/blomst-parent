package com.anjunar.common.rest.api.json.factories;

import com.google.common.reflect.TypeToken;
import com.anjunar.common.rest.api.json.schema.JsonNumber;

@SuppressWarnings("UnstableApiUsage")
public class DoubleFactory extends NumericFactory<JsonNumber> {
    @Override
    public boolean test(TypeToken<?> typeToken) {
        return typeToken.getRawType().equals(Double.class) || typeToken.getType().equals(double.class);
    }

    @Override
    public JsonNumber build(TypeToken<?> typeToken) {
        return new JsonNumber();
    }

}
