package com.anjunar.common.rest.schema;

import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonTypeName("Category")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true)
public class CategoryType extends AbstractRestEntity {

    @NotBlank
    @Size(min = 3, max = 80)
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Name", naming = true)
    @JsonProperty(required = true)
    private String name;

    @Size(max = 255)
    @JsonSchema(widget = JsonNode.Widget.TEXTAREA, title = "Description")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
