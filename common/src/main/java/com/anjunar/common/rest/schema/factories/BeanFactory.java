package com.anjunar.common.rest.schema.factories;

import com.anjunar.common.rest.mapper.annotations.MapperView;
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
    public JsonObject build(TypeToken<?> typeToken, BeanProperty<?, ?> property) {
        MapperView mapperView = property.getAnnotation(MapperView.class);
        if (Objects.nonNull(mapperView)) {
            return generateObject(typeToken.getRawType(), mapperView.value());
        }
        return generateObject(typeToken.getRawType(), null);
    }

    @Override
    public JsonObject buildWithAnnotation(BeanProperty<?, ?> property) {
        JsonObject jsonObject = super.buildWithAnnotation(property);
        return jsonObject;
    }
}
