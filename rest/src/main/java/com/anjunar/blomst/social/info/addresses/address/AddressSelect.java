package com.anjunar.blomst.social.info.addresses.address;

import com.anjunar.blomst.social.info.addresses.MapBoxAddress;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.schema.CategoryType;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;

import java.util.Set;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class AddressSelect extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Full Address")
    private MapBoxAddress name;

    public MapBoxAddress getName() {
        return name;
    }

    public void setName(MapBoxAddress name) {
        this.name = name;
    }
}
