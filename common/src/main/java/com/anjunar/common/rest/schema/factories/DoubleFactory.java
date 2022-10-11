package com.anjunar.common.rest.schema.factories;

import com.anjunar.common.rest.schema.JsonContext;
import com.anjunar.introspector.bean.BeanProperty;
import com.google.common.reflect.TypeToken;
import com.anjunar.common.rest.schema.schema.JsonDouble;

@SuppressWarnings("UnstableApiUsage")
public class DoubleFactory extends NumericFactory<JsonDouble> {
    @Override
    public boolean test(TypeToken<?> typeToken) {
        return typeToken.getRawType().equals(Double.class) || typeToken.getType().equals(double.class);
    }

    @Override
    public JsonDouble build(TypeToken<?> typeToken, BeanProperty<?, ?> property, JsonContext context) {
        return new JsonDouble();
    }

}
