package com.anjunar.common.rest.schema.schema;

import com.anjunar.common.rest.schema.JsonSchemaEntry;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.*;

@JsonTypeName("object")
public class JsonObject extends JsonNode {

    private LinkedHashMap<String, JsonNode> properties = new LinkedHashMap<>();

    private Boolean additionalProperties;

    private Boolean unevaluatedProperties;

    private List<String> required;

    private Integer minProperties;

    private Integer maxProperties;

    public void remove(String key) {
        properties.remove(key);
    }

    public <J extends JsonNode> J find(String name, Class<J> type) {
        for (Map.Entry<String, JsonNode> entry : properties.entrySet()) {
            if (entry.getKey().equals(name)) {
                J result = (J) entry.getValue();
                if (type.isAssignableFrom(result.getClass())) {
                    return result;
                }
            }
        }
        for (Map.Entry<String, JsonNode> entry : properties.entrySet()) {
            if (entry.getValue() instanceof JsonObject) {
                JsonObject jsonObject = (JsonObject) entry.getValue();
                JsonNode result = jsonObject.find(name, type);
                if (result != null) {
                    if (type.isAssignableFrom(result.getClass())) {
                        return (J) result;
                    }
                }
            }
            if (entry.getValue() instanceof JsonArray) {
                JsonArray jsonArray = (JsonArray) entry.getValue();
                J result = jsonArray.find(name, type);
                if (result != null) {
                    if (type.isAssignableFrom(result.getClass())) {
                        return result;
                    }
                }
            }
        }

        return null;
    }

    public List<JsonSchemaEntry<? extends JsonNode>> find(Widget widget) {
        for (Map.Entry<String, JsonNode> entry : properties.entrySet()) {
            List<JsonSchemaEntry<? extends JsonNode>> result = new ArrayList<>();
            Widget widgetCursor = entry.getValue().getWidget();
            if (widgetCursor != null && widgetCursor.equals(widget)) {
                result.add(new JsonSchemaEntry<>(entry.getKey(), (JsonNode) entry.getValue()));
                return result;
            }
            if (entry.getValue() instanceof JsonObject) {
                JsonObject jsonObject = (JsonObject) entry.getValue();
                List<JsonSchemaEntry<? extends JsonNode>> jsonNodes = jsonObject.find(widget);
                result.addAll(jsonNodes);
            }
            if (entry.getValue() instanceof JsonArray) {
                JsonArray jsonArray = (JsonArray) entry.getValue();
                return jsonArray.find(widget);
            }
        }
        return new ArrayList<>();
    }

    public LinkedHashMap<String, JsonNode> getProperties() {
        return properties;
    }

    public void setProperties(LinkedHashMap<String, JsonNode> properties) {
        this.properties = properties;
    }

    public Boolean getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Boolean additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public Boolean getUnevaluatedProperties() {
        return unevaluatedProperties;
    }

    public void setUnevaluatedProperties(Boolean unevaluatedProperties) {
        this.unevaluatedProperties = unevaluatedProperties;
    }

    public List<String> getRequired() {
        return required;
    }

    public void setRequired(List<String> required) {
        this.required = required;
    }

    public Integer getMinProperties() {
        return minProperties;
    }

    public void setMinProperties(Integer minProperties) {
        this.minProperties = minProperties;
    }

    public Integer getMaxProperties() {
        return maxProperties;
    }

    public void setMaxProperties(Integer maxProperties) {
        this.maxProperties = maxProperties;
    }

}
