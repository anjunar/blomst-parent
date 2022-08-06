package com.anjunar.common.rest.api.json.factories;

import com.google.common.reflect.TypeToken;
import com.anjunar.common.rest.api.json.schema.JsonObject;
import de.anjunar.introspector.bean.BeanProperty;

import static com.anjunar.common.rest.api.json.JsonSchemaGenerator.generateObject;

@SuppressWarnings("UnstableApiUsage")
public class BeanFactory extends JsonAbstractFactory<JsonObject> {
    @Override
    public boolean test(TypeToken<?> typeToken) {
        return typeToken.isSubtypeOf(Object.class);
    }

    @Override
    public JsonObject build(TypeToken<?> typeToken) {
        return generateObject(typeToken.getRawType());
    }

    @Override
    public JsonObject buildWithAnnotation(BeanProperty<?, ?> property) {
        JsonObject jsonObject = super.buildWithAnnotation(property);
        return jsonObject;
    }
}
