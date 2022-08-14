package com.anjunar.common.rest.api;

import com.anjunar.common.rest.schema.JsonSchemaEntry;
import com.anjunar.common.rest.schema.JsonSchemaGenerator;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AbstractSchemaEntity {

    @JsonSchema(ignore = true)
    @JsonProperty(value = "$schema")
    protected final JsonObject schema;

    public AbstractSchemaEntity() {
        schema = JsonSchemaGenerator.generateObject(getClass());
    }

    public JsonObject getSchema() {
        return schema;
    }

    public void addLink(String rel, Link link) {
        schema.getLinks().put(rel, link);
    }

    public <J extends JsonNode> J find(String name, Class<J> type) {
        return schema.find(name, type);
    }

    public List<JsonSchemaEntry<? extends JsonNode>> find(JsonNode.Widget widget) {
        return schema.find(widget);
    }

}
