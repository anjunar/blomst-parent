package com.anjunar.blomst.system;

import com.anjunar.common.rest.api.AbstractSchemaEntity;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;

import java.util.Properties;

public class SystemForm extends AbstractSchemaEntity {

    @JsonSchema(widget = JsonNode.Widget.JSON, title = "Properties")
    Properties properties;

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
