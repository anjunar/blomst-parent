package com.anjunar.common.rest.api.json.factories;

import com.anjunar.common.rest.api.json.validators.SizeValidator;
import com.google.common.reflect.TypeToken;
import com.anjunar.common.rest.api.json.schema.JsonArray;
import de.anjunar.introspector.bean.BeanProperty;

import javax.validation.constraints.Size;
import java.util.Collection;

import static com.anjunar.common.rest.api.json.JsonSchemaGenerator.generateArray;

@SuppressWarnings("UnstableApiUsage")
public class CollectionFactory extends JsonAbstractFactory<JsonArray> {

    @Override
    public boolean test(TypeToken<?> typeToken) {
        return typeToken.isSubtypeOf(Collection.class);
    }

    @Override
    public JsonArray build(TypeToken<?> typeToken) {
        TypeToken<?> collectionType = typeToken.resolveType(Collection.class.getTypeParameters()[0]);
        return generateArray(collectionType);
    }

    @Override
    public JsonArray buildWithAnnotation(BeanProperty<?, ?> property) {
        JsonArray jsonArray = super.buildWithAnnotation(property);

        Size size = property.getAnnotation(Size.class);
        if (size != null) {
            jsonArray.addValidator("size", new SizeValidator(size.min(), size.max()));
        }

        return jsonArray;
    }
}
