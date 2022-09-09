package com.anjunar.blomst.control.users.user.connections.connection;

import com.anjunar.blomst.control.users.user.UserForm;
import com.anjunar.blomst.control.users.user.UserSelect;
import com.anjunar.blomst.control.users.user.connections.categories.category.CategoryForm;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.mapper.annotations.MapperView;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.annotations.JsonSchemaReadOnly;
import com.anjunar.common.rest.schema.schema.JsonNode;
import jakarta.validation.constraints.NotNull;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class UserConnectionForm extends AbstractRestEntity {

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "From")
    @JsonSchemaReadOnly
    @MapperView(UserSelect.class)
    private UserForm from;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Category")
    private CategoryForm category;

    @JsonSchema(widget = JsonNode.Widget.CHECKBOX, title = "Accepted")
    @JsonSchemaReadOnly
    private boolean accepted;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "To")
    @JsonSchemaReadOnly
    @MapperView(UserSelect.class)
    private UserForm to;

    public UserForm getFrom() {
        return from;
    }

    public void setFrom(UserForm from) {
        this.from = from;
    }

    public CategoryForm getCategory() {
        return category;
    }

    public void setCategory(CategoryForm category) {
        this.category = category;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public UserForm getTo() {
        return to;
    }

    public void setTo(UserForm to) {
        this.to = to;
    }

}
