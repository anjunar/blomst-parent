package com.anjunar.common.rest.schema.factories;

import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.annotations.JsonSchemaReadOnly;
import com.anjunar.common.rest.schema.validators.NotNullValidator;
import com.google.common.reflect.TypeToken;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.introspector.bean.BeanProperty;

import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@SuppressWarnings("UnstableApiUsage")
public abstract class JsonAbstractFactory<J extends JsonNode> {

    public abstract boolean test(TypeToken<?> typeToken);

    public abstract J build(TypeToken<?> typeToken, BeanProperty<?, ?> property);

    public J buildWithAnnotation(BeanProperty<?, ?> property) {
        J jsonNode = build(property.getType(), property);
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
            if (jsonSchema.visibility()) {
                jsonNode.setVisibility(true);
            }
        }

        JsonSchemaReadOnly readOnly = property.getAnnotation(JsonSchemaReadOnly.class);
        if (Objects.nonNull(readOnly)) {
            jsonNode.setReadOnly(true);
        }

        NotNull notNull = property.getAnnotation(NotNull.class);
        if (notNull != null) {
            jsonNode.addValidator("notNull", new NotNullValidator());
        }

        return jsonNode;
    }

}
