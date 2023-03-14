package com.anjunar.blomst.shared.likes.likeable;

import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;

public class LikesType {

    @JsonSchema(widget = JsonNode.Widget.CHECKBOX, title = "Active", readOnly = true)
    private boolean active = false;

    @JsonSchema(widget = JsonNode.Widget.NUMBER, title = "Count", readOnly = true)
    private int count = 0;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
