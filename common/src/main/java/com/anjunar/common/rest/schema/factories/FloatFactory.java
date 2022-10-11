package com.anjunar.common.rest.schema.factories;

import com.anjunar.common.rest.schema.JsonContext;
import com.anjunar.common.rest.schema.schema.JsonFloat;
import com.anjunar.introspector.bean.BeanProperty;
import com.google.common.reflect.TypeToken;
import com.anjunar.common.rest.schema.schema.JsonDouble;

@SuppressWarnings("UnstableApiUsage")
public class FloatFactory extends NumericFactory<JsonFloat> {
    @Override
    public boolean test(TypeToken<?> typeToken) {
        return typeToken.getRawType().equals(Float.class) || typeToken.getType().equals(float.class);
    }

    @Override
    public JsonFloat build(TypeToken<?> typeToken, BeanProperty<?, ?> property, JsonContext context) {
        return new JsonFloat();
    }

}
