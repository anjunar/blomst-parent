package com.anjunar.common.rest.api;

import com.anjunar.common.rest.api.json.JsonSchemaGenerator;
import com.anjunar.common.rest.api.json.schema.JsonNode;
import com.anjunar.common.rest.api.json.schema.JsonObject;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MetaTable<E> implements LinksContainer {

    @JsonProperty(value = "$schema")
    private JsonObject schema;

    public MetaTable(Class<E> aClass) {
        if (aClass != null) {
            schema = JsonSchemaGenerator.generateObject(aClass);
            schema.setWidget(JsonNode.Widget.TABLE);
        }
    }

    public MetaTable() {
        this(null);
    }

    public JsonObject getSchema() {
        return schema;
    }

    @Override
    public void addLink(String rel, Link link) {
        schema.addLink(rel, link);
    }

    public <J extends JsonNode> J find(String name, Class<J> type) {
        return schema.find(name, type);
    }
}
