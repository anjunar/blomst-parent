package com.anjunar.common.rest.api.json;

import com.anjunar.common.rest.api.json.schema.JsonNode;

public class JsonSchemaEntry<J extends JsonNode> {

    private String name;

    private J node;

    public JsonSchemaEntry(String name, J node) {
        this.name = name;
        this.node = node;
    }

    public String getName() {
        return name;
    }

    public J getNode() {
        return node;
    }

}
