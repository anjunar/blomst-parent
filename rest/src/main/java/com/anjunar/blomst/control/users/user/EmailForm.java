package com.anjunar.blomst.control.users.user;

import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class EmailForm {

    @JsonSchema(widget = JsonNode.Widget.EMAIL, title = "Email", naming = true)
    @Email
    @NotBlank
    private String value;

    @JsonSchema(widget = JsonNode.Widget.CHECKBOX, title = "Confirmed", readOnly = true)
    private boolean confirmed;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }


}
