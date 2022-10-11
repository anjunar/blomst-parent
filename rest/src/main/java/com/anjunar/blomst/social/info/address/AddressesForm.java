package com.anjunar.blomst.social.info.address;

import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.mapper.annotations.MapperPermanent;
import com.anjunar.common.rest.mapper.annotations.MapperVisibility;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;

import java.util.ArrayList;
import java.util.List;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class AddressesForm extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Owner", readOnly = true)
    @MapperPermanent
    private UserSelect owner;

    @JsonSchema(widget = JsonNode.Widget.REPEAT, title = "Addresses")
    @MapperVisibility
    private List<AddressForm> items = new ArrayList<>();

    public UserSelect getOwner() {
        return owner;
    }

    public void setOwner(UserSelect owner) {
        this.owner = owner;
    }

    public List<AddressForm> getItems() {
        return items;
    }

    public void setItems(List<AddressForm> items) {
        this.items = items;
    }
}
