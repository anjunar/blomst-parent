package com.anjunar.common.rest.schema;

import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.factories.JsonAbstractFactory;
import com.anjunar.common.rest.schema.schema.JsonArray;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.google.common.reflect.TypeToken;
import com.anjunar.introspector.bean.BeanIntrospector;
import com.anjunar.introspector.bean.BeanModel;
import com.anjunar.introspector.bean.BeanProperty;
import com.anjunar.introspector.type.TypeResolver;
import com.anjunar.introspector.type.resolved.ResolvedType;

@SuppressWarnings("UnstableApiUsage")
public class JsonSchemaGenerator {

    public static <E> JsonObject generateObject(Class<E> aClass) {
        ResolvedType<E> resolvedType = TypeResolver.resolve(aClass);
        JsonObject jsonObject = new JsonObject();

        JsonSchema jsonSchemaObject = resolvedType.getAnnotation(JsonSchema.class);
        if (jsonSchemaObject != null) {
            if (jsonSchemaObject.title().length() > 0) {
                jsonObject.setTitle(jsonSchemaObject.title());
            }
            if (jsonSchemaObject.description().length() > 0) {
                jsonObject.setDescription(jsonSchemaObject.description());
            }
            if (!jsonSchemaObject.widget().equals(JsonNode.Widget.NO_WIDGET)) {
                jsonObject.setWidget(jsonSchemaObject.widget());
            }
        }

        BeanModel<E> beanModel = BeanIntrospector.create(aClass);
        for (BeanProperty<E, ?> property : beanModel.getProperties()) {
            JsonSchema jsonSchema = property.getAnnotation(JsonSchema.class);
            if (jsonSchema == null || ! jsonSchema.ignore()) {
                TypeToken<?> type = property.getType();
                for (JsonAbstractFactory<?> factory : JsonRegistry.factories) {
                    if (factory.test(type)) {
                        JsonNode jsonNode = factory.buildWithAnnotation(property);
                        jsonNode.setName(property.getKey());
                        jsonObject.getProperties().put(property.getKey(), jsonNode);
                        break;
                    }
                }
            }
        }
        return jsonObject;
    }

    public static JsonArray generateArray(TypeToken<?> type) {
        JsonArray jsonArray = new JsonArray();
        for (JsonAbstractFactory<?> factory : JsonRegistry.factories) {
            if (factory.test(type)) {
                JsonNode jsonNode = factory.build(type);
                jsonArray.setItems(jsonNode);
                break;
            }
        }
        return jsonArray;
    }


}
