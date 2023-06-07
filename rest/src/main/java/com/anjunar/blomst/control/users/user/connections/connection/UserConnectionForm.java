package com.anjunar.blomst.control.users.user.connections.connection;

import com.anjunar.blomst.control.users.user.connections.categories.category.CategoryForm;
import com.anjunar.blomst.shared.users.user.UserReference;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.security.UserConnection;
import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.NotNull;

@JsonSchema(widget = JsonNode.Widget.FORM)
@JsonTypeName("UserConnection")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true)
public class UserConnectionForm extends AbstractRestEntity {

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.REFERENCE, title = "From", readOnly = true)
    private UserReference from;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Category")
    private CategoryForm category;

    @JsonSchema(widget = JsonNode.Widget.SELECT, title = "Status", readOnly = true)
    private UserConnection.ConnectionStatus status;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "To")
    private UserSelect to;

    public UserReference getFrom() {
        return from;
    }

    public void setFrom(UserReference from) {
        this.from = from;
    }

    public CategoryForm getCategory() {
        return category;
    }

    public void setCategory(CategoryForm category) {
        this.category = category;
    }

    public UserConnection.ConnectionStatus getStatus() {
        return status;
    }

    public void setStatus(UserConnection.ConnectionStatus status) {
        this.status = status;
    }

    public UserSelect getTo() {
        return to;
    }

    public void setTo(UserSelect to) {
        this.to = to;
    }

}
