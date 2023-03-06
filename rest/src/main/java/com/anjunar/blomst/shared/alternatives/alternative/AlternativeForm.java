package com.anjunar.blomst.shared.alternatives.alternative;

import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("Alternative")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true)
public class AlternativeForm extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Value", naming = true)
    private String value;

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Property")
    private String property;

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Entity")
    private String entity;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Owner")
    private UserSelect owner;

    @JsonSchema(widget = JsonNode.Widget.NUMBER, title = "Count", naming = true)
    private Long count;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public UserSelect getOwner() {
        return owner;
    }

    public void setOwner(UserSelect owner) {
        this.owner = owner;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
