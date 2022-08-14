package com.anjunar.blomst.social.info.resume;

import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.control.users.Resume;
import com.anjunar.blomst.control.users.ResumeItem;
import com.anjunar.blomst.shared.users.user.UserSelect;

import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class ResumeForm extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Owner", readOnly = true)
    private UserSelect owner;

    @JsonSchema(widget = JsonNode.Widget.REPEAT, title = "Items")
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
