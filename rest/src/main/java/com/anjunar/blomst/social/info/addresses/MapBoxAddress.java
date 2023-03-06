package com.anjunar.blomst.social.info.addresses;

import com.anjunar.common.rest.api.AbstractSchemaEntity;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class MapBoxAddress extends AbstractSchemaEntity {

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Name", naming = true, id = true)
    private String name = "";

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Description")
    private String description = "";

    @JsonSchema(widget = JsonNode.Widget.FORM, title = "Point")
    private MapBoxPoint point = new MapBoxPoint();

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

    public void setPoint(MapBoxPoint point) {
        this.point = point;
    }

    public MapBoxPoint getPoint() {
        return point;
    }
}
