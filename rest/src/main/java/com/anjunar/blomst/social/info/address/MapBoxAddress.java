package com.anjunar.blomst.social.info.address;

import com.anjunar.common.rest.api.AbstractSchemaEntity;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class MapBoxAddress extends AbstractSchemaEntity {

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Name", naming = true, id = true)
    private String name;

    @JsonSchema(widget = JsonNode.Widget.FORM, title = "Point")
    private Point point;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }
}
