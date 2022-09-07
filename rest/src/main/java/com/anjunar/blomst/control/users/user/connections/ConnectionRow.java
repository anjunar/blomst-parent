package com.anjunar.blomst.control.users.user.connections;

import com.anjunar.blomst.control.users.user.UserForm;
import com.anjunar.blomst.control.users.user.connections.categories.category.CategoryForm;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import jakarta.validation.constraints.NotNull;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class ConnectionRow extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Category")
    private CategoryForm category;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "To", readOnly = true)
    private UserForm to;

    @JsonSchema(widget = JsonNode.Widget.CHECKBOX, title = "Accepted", readOnly = true)
    private boolean accepted;

    public CategoryForm getCategory() {
        return category;
    }

    public void setCategory(CategoryForm category) {
        this.category = category;
    }

    public UserForm getTo() {
        return to;
    }

    public void setTo(UserForm to) {
        this.to = to;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }


}
