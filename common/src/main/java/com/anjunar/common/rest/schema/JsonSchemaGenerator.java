package com.anjunar.common.rest.schema;

import com.anjunar.common.rest.mapper.annotations.MapperSecurity;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.factories.JsonAbstractFactory;
import com.anjunar.common.rest.schema.schema.JsonArray;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.introspector.bean.BeanIntrospector;
import com.anjunar.introspector.bean.BeanModel;
import com.anjunar.introspector.bean.BeanProperty;
import com.anjunar.introspector.type.TypeResolver;
import com.anjunar.introspector.type.resolved.ResolvedType;
import com.google.common.reflect.TypeToken;
import jakarta.enterprise.inject.spi.CDI;

import java.util.Collection;
import java.util.Objects;

@SuppressWarnings("UnstableApiUsage")
public class JsonSchemaGenerator {

    public static <E> JsonObject generateObject(Class<E> aClass) {
        return generateObject(aClass, null, new JsonContext());
    }

    public static <E> JsonObject generateObject(Class<E> aClass, Class<?> projectionClass, JsonContext context) {
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
        BeanModel<?> projectionModel;
        if (Objects.nonNull(projectionClass)) {
            projectionModel = BeanIntrospector.create(projectionClass);
        } else {
            projectionModel = beanModel;
        }

        for (BeanProperty<E, ?> property : beanModel.getProperties()) {
            JsonSchema jsonSchema = property.getAnnotation(JsonSchema.class);
            if (jsonSchema == null || ! jsonSchema.ignore()) {
                BeanProperty<?, ?> projectionProperty = projectionModel.get(property.getKey());
                if (Objects.nonNull(projectionProperty)) {
                    MapperSecurity mapperSecurity = property.getAnnotation(MapperSecurity.class);
                    if (Objects.nonNull(mapperSecurity)) {
                        String[] rolesAllowed = mapperSecurity.rolesAllowed();
                        IdentityManager identityManager = CDI.current().select(IdentityManager.class).get();
                        if (identityManager.hasRole(rolesAllowed)) {
                            process(jsonObject, property, context);
                        }
                    } else {
                        process(jsonObject, property, context);
                    }
                }
            }
        }
        return jsonObject;
    }

    private static <E> void process(JsonObject jsonObject, BeanProperty<E, ?> property, JsonContext context) {
        JsonSchema jsonSchema = property.getAnnotation(JsonSchema.class);
        if (! Objects.nonNull(jsonSchema) || ! jsonSchema.cycle() || ! context.getProperties().contains(property)) {
            context.getProperties().add(property);

            TypeToken<?> type = property.getType();
            for (JsonAbstractFactory<?> factory : JsonRegistry.factories) {
                if (factory.test(type)) {
                    JsonNode jsonNode = factory.buildWithAnnotation(property, context);
                    jsonObject.getProperties().put(property.getKey(), jsonNode);
                    break;
                }
            }
        }
    }

    public static JsonArray generateArray(BeanProperty<?, ?> property, JsonContext context) {
        TypeToken<?> type = property.getType().resolveType(Collection.class.getTypeParameters()[0]);
        JsonArray jsonArray = new JsonArray();
        for (JsonAbstractFactory<?> factory : JsonRegistry.factories) {
            if (factory.test(type)) {
                JsonNode jsonNode = factory.build(type, property, context);
                jsonArray.setItems(jsonNode);
                break;
            }
        }
        return jsonArray;
    }


}
