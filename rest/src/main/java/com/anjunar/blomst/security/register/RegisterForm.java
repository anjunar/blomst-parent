package com.anjunar.blomst.security.register;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.anjunar.common.rest.api.Link;
import com.anjunar.common.rest.api.LinksContainer;
import com.anjunar.common.rest.schema.JsonSchemaGenerator;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.rest.schema.schema.JsonObject;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@NaturalId
public class RegisterForm implements LinksContainer {

    @Email
    @NotBlank
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "First Name")
    private String email;

    @Size(min = 3, max = 80)
    @NotBlank
    @JsonSchema(widget = JsonNode.Widget.PASSWORD, title = "Password")
    private String password;

    @JsonSchema(ignore = true)
    @JsonProperty(value = "$schema")
    private final JsonObject schema = JsonSchemaGenerator.generateObject(RegisterForm.class);

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addLink(String rel, Link link) {
        schema.getLinks().put(rel, link);
    }

    public JsonObject getSchema() {
        return schema;
    }
}
