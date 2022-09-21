package com.anjunar.blomst.control.users.user.connections.connection;

import com.anjunar.blomst.control.users.user.UserForm;
import com.anjunar.blomst.control.users.user.connections.categories.category.CategoryForm;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.mapper.annotations.MapperView;
import com.anjunar.common.rest.mapper.annotations.MapperWrite;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import jakarta.validation.constraints.NotNull;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class UserConnectionForm extends AbstractRestEntity {

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "From", readOnly = true)
    @MapperWrite
    private UserSelect from;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Category")
    @MapperWrite
    private CategoryForm category;

    @JsonSchema(widget = JsonNode.Widget.CHECKBOX, title = "Accepted", readOnly = true)
    private boolean accepted;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "To", readOnly = true)
    @MapperWrite
    private UserSelect to;

    public UserSelect getFrom() {
        return from;
    }

    public void setFrom(UserSelect from) {
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

    public UserSelect getTo() {
        return to;
    }

    public void setTo(UserSelect to) {
        this.to = to;
    }

}
