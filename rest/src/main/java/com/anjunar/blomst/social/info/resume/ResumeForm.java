package com.anjunar.blomst.social.info.resume;

import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.mapper.annotations.MapperVisibility;
import com.anjunar.common.rest.mapper.annotations.MapperWrite;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;

import java.util.ArrayList;
import java.util.List;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class ResumeForm extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Owner", readOnly = true)
    @MapperWrite
    private UserSelect owner;

    @JsonSchema(widget = JsonNode.Widget.REPEAT, title = "Items", visibility = true)
    private final List<ResumeItemForm> items = new ArrayList<>();

    public UserSelect getOwner() {
        return owner;
    }

    public void setOwner(UserSelect owner) {
        this.owner = owner;
    }

    public List<ResumeItemForm> getItems() {
        return items;
    }

}
