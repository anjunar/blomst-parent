package com.anjunar.common.rest.schema.factories;

import com.anjunar.common.rest.mapper.annotations.MapperView;
import com.anjunar.common.rest.schema.JsonContext;
import com.google.common.reflect.TypeToken;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.introspector.bean.BeanProperty;

import java.util.Objects;

import static com.anjunar.common.rest.schema.JsonSchemaGenerator.generateObject;

@SuppressWarnings("UnstableApiUsage")
public class BeanFactory extends JsonAbstractFactory<JsonObject> {
    @Override
    public boolean test(TypeToken<?> typeToken) {
        return typeToken.isSubtypeOf(Object.class);
    }

    @Override
    public JsonObject build(TypeToken<?> typeToken, BeanProperty<?, ?> property, JsonContext context) {
        MapperView mapperView = property.getAnnotation(MapperView.class);
        if (Objects.nonNull(mapperView)) {
            return generateObject(typeToken.getRawType(), mapperView.value(), context);
        }
        return generateObject(typeToken.getRawType(), null, context);
    }

    @Override
    public JsonObject buildWithAnnotation(BeanProperty<?, ?> property, JsonContext context) {
        JsonObject jsonObject = super.buildWithAnnotation(property, context);
        return jsonObject;
    }
}
