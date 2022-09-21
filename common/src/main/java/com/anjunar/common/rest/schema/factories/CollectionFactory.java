package com.anjunar.common.rest.schema.factories;

import com.anjunar.common.rest.schema.JsonContext;
import com.anjunar.common.rest.schema.validators.SizeValidator;
import com.google.common.reflect.TypeToken;
import com.anjunar.common.rest.schema.schema.JsonArray;
import com.anjunar.introspector.bean.BeanProperty;

import jakarta.validation.constraints.Size;
import java.util.Collection;

import static com.anjunar.common.rest.schema.JsonSchemaGenerator.generateArray;

@SuppressWarnings("UnstableApiUsage")
public class CollectionFactory extends JsonAbstractFactory<JsonArray> {

    @Override
    public boolean test(TypeToken<?> typeToken) {
        return typeToken.isSubtypeOf(Collection.class);
    }

    @Override
    public JsonArray build(TypeToken<?> typeToken, BeanProperty<?, ?> property, JsonContext context) {
        return generateArray(property, context);
    }

    @Override
    public JsonArray buildWithAnnotation(BeanProperty<?, ?> property, JsonContext context) {
        JsonArray jsonArray = super.buildWithAnnotation(property, context);

        Size size = property.getAnnotation(Size.class);
        if (size != null) {
            jsonArray.addValidator("size", new SizeValidator(size.min(), size.max()));
        }

        return jsonArray;
    }
}
