package com.anjunar.common.rest.api.json.factories;

import com.anjunar.common.rest.api.json.annotations.JsonSchema;
import com.anjunar.common.rest.api.json.validators.NotNullValidator;
import com.google.common.reflect.TypeToken;
import com.anjunar.common.rest.api.json.schema.JsonNode;
import de.anjunar.introspector.bean.BeanProperty;

import javax.validation.constraints.NotNull;

@SuppressWarnings("UnstableApiUsage")
public abstract class JsonAbstractFactory<J extends JsonNode> {

    public abstract boolean test(TypeToken<?> typeToken);

    public abstract J build(TypeToken<?> typeToken);

    public J buildWithAnnotation(BeanProperty<?, ?> property) {
        J jsonNode = build(property.getType());
        JsonSchema jsonSchema = property.getAnnotation(JsonSchema.class);
        if (jsonSchema != null) {
            if (jsonSchema.readOnly()) {
                jsonNode.setReadOnly(true);
            }
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
        }

        NotNull notNull = property.getAnnotation(NotNull.class);
        if (notNull != null) {
            jsonNode.addValidator("notNull", new NotNullValidator());
        }

        return jsonNode;
    }

}
