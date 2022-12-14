package com.anjunar.common.rest.schema.factories;

import com.anjunar.common.rest.mapper.annotations.MapperVisibility;
import com.anjunar.common.rest.schema.JsonContext;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.validators.NotNullValidator;
import com.google.common.reflect.TypeToken;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.introspector.bean.BeanProperty;

import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Objects;

@SuppressWarnings("UnstableApiUsage")
public abstract class JsonAbstractFactory<J extends JsonNode> {

    public abstract boolean test(TypeToken<?> typeToken);

    public abstract J build(TypeToken<?> typeToken, BeanProperty<?, ?> property, JsonContext context);

    public J buildWithAnnotation(BeanProperty<?, ?> property, JsonContext context) {
        J jsonNode = build(property.getType(), property, context);
        JsonSchema jsonSchema = property.getAnnotation(JsonSchema.class);
        if (jsonSchema != null) {
            if (jsonSchema.title().length() > 0) {
                jsonNode.setTitle(jsonSchema.title());
            }
            if (jsonSchema.description().length() > 0) {
                jsonNode.setDescription(jsonSchema.description());
            }
            if (jsonSchema.id()) {
                jsonNode.setId(true);
            }
            if (jsonSchema.naming()) {
                jsonNode.setNaming(true);
            }
            if (! jsonSchema.widget().equals(JsonNode.Widget.NO_WIDGET)) {
                jsonNode.setWidget(jsonSchema.widget());
            }
            MapperVisibility visibility = property.getAnnotation(MapperVisibility.class);
            if (Objects.nonNull(visibility)) {
                jsonNode.setVisibility(new HashSet<>());
            }
            if (jsonSchema.readOnly()) {
                jsonNode.setReadOnly(true);
            }
        }

        NotNull notNull = property.getAnnotation(NotNull.class);
        if (notNull != null) {
            jsonNode.addValidator("notNull", new NotNullValidator());
        }

        return jsonNode;
    }

}
