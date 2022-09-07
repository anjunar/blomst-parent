package com.anjunar.blomst.control.users.user;

import com.anjunar.common.rest.api.AbstractSchemaEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.anjunar.common.rest.schema.JsonSchemaGenerator;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.security.EmailType;
import com.anjunar.common.security.IdentityProvider;

import jakarta.persistence.EntityManager;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class EmailForm extends AbstractSchemaEntity {

    @JsonSchema(widget = JsonNode.Widget.EMAIL, title = "Email")
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
