package com.anjunar.blomst.social.info.addresses.address;

import com.anjunar.blomst.social.info.addresses.MapBoxAddress;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.schema.CategoryType;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Set;

@JsonSchema(widget = JsonNode.Widget.FORM)
@JsonTypeName("Address")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true)
public class AddressSelect extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Full Address")
    private MapBoxAddress name = new MapBoxAddress();

    public MapBoxAddress getName() {
        return name;
    }

    public void setName(MapBoxAddress name) {
        this.name = name;
    }
}
