package com.anjunar.common.rest.schema.schema;

import com.anjunar.common.rest.schema.JsonSchemaEntry;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.List;

@JsonTypeName("array")
public class JsonArray extends JsonNode {

    private JsonNode items;

    private Integer minContains;

    private Integer maxContains;

    private Boolean uniqueItems;

    public <J extends JsonNode> J find(String name, Class<J> type) {
        if (items instanceof JsonObject) {
            JsonObject value = (JsonObject) items;
            return value.find(name, type);
        }
        return null;
    }

    public List<JsonSchemaEntry<? extends JsonNode>> find(JsonNode.Widget widget) {
        if (items instanceof JsonObject) {
            JsonObject value = (JsonObject) items;
            return value.find(widget);
        }
        return null;
    }

    public JsonNode getItems() {
        return items;
    }

    public void setItems(JsonNode items) {
        this.items = items;
    }

    public Integer getMinContains() {
        return minContains;
    }

    public void setMinContains(Integer minContains) {
        this.minContains = minContains;
    }

    public Integer getMaxContains() {
        return maxContains;
    }

    public void setMaxContains(Integer maxContains) {
        this.maxContains = maxContains;
    }

    public Boolean getUniqueItems() {
        return uniqueItems;
    }

    public void setUniqueItems(Boolean uniqueItems) {
        this.uniqueItems = uniqueItems;
    }
}
