package com.anjunar.blomst.security.logout;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.anjunar.common.rest.api.Link;
import com.anjunar.common.rest.api.LinksContainer;
import com.anjunar.common.rest.api.json.JsonSchemaGenerator;
import com.anjunar.common.rest.api.json.annotations.JsonSchema;
import com.anjunar.common.rest.api.json.schema.JsonNode;
import com.anjunar.common.rest.api.json.schema.JsonObject;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class LogoutForm implements LinksContainer {

    @JsonSchema(ignore = true)
    @JsonProperty(value = "$schema", access = JsonProperty.Access.READ_ONLY)
    private final JsonObject schema = JsonSchemaGenerator.generateObject(LogoutForm.class);

    public JsonObject getSchema() {
        return schema;
    }

    @Override
    public void addLink(String rel, Link link) {
        schema.getLinks().put(rel, link);
    }

}
