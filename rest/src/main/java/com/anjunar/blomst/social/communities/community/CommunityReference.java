package com.anjunar.blomst.social.communities.community;

import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.NamedReference;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;


@JsonTypeName("Community")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true)
public class CommunityReference extends AbstractRestEntity implements NamedReference {

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Name", readOnly = true, naming = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
