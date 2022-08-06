package com.anjunar.blomst.control.users.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.anjunar.common.rest.api.json.JsonSchemaGenerator;
import com.anjunar.common.rest.api.json.annotations.JsonSchema;
import com.anjunar.common.rest.api.json.schema.JsonNode;
import com.anjunar.common.rest.api.json.schema.JsonObject;
import com.anjunar.common.security.EmailType;
import com.anjunar.common.security.IdentityProvider;

import javax.persistence.EntityManager;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class EmailForm {

    @JsonSchema(widget = JsonNode.Widget.EMAIL, title = "Email")
    private String value;

    @JsonSchema(widget = JsonNode.Widget.CHECKBOX, title = "Confirmed", readOnly = true)
    private boolean confirmed;

    @JsonProperty("$schema")
    @JsonSchema(ignore = true)
    private final JsonObject schema = JsonSchemaGenerator.generateObject(EmailForm.class);

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

    public JsonObject getSchema() {
        return schema;
    }

    public static EmailForm factory(EmailType entity) {
        EmailForm form = new EmailForm();
        form.setValue(entity.getValue());
        form.setConfirmed(entity.isConfirmed());
        return form;
    }

    public static EmailType updater(EmailForm resource, EmailType entity, EntityManager entityManager, IdentityProvider identityProvider) {
        entity.setValue(resource.getValue());
        return entity;
    }

}
