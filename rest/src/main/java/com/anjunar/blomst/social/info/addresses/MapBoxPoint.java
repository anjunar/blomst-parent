package com.anjunar.blomst.social.info.addresses;

import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;

public class MapBoxPoint {

    @JsonSchema(widget = JsonNode.Widget.NUMBER, title = "X")
    private Float x;

    @JsonSchema(widget = JsonNode.Widget.NUMBER, title = "Y")
    private Float y;

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }
}
