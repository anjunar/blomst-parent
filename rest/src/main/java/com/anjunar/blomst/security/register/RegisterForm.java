package com.anjunar.blomst.security.register;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.anjunar.common.rest.api.Link;
import com.anjunar.common.rest.api.LinksContainer;
import com.anjunar.common.rest.api.json.JsonSchemaGenerator;
import com.anjunar.common.rest.api.json.annotations.JsonSchema;
import com.anjunar.common.rest.api.json.schema.JsonNode;
import com.anjunar.common.rest.api.json.schema.JsonObject;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NaturalId
public class RegisterForm implements LinksContainer {

    @Size(min = 3, max = 80)
    @NotBlank
    @JsonSchema(widget = JsonNode.Widget.TEXT)
    private String firstName;

    @Size(min = 3, max = 80)
    @NotBlank
    @JsonSchema(widget = JsonNode.Widget.TEXT)
    private String lastName;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.DATE)
    private LocalDate birthDate;

    @Size(min = 3, max = 80)
    @NotBlank
    @JsonSchema(widget = JsonNode.Widget.PASSWORD)
    private String password;

    @JsonSchema(ignore = true)
    @JsonProperty(value = "$schema", access = JsonProperty.Access.READ_ONLY)
    private final JsonObject schema = JsonSchemaGenerator.generateObject(RegisterForm.class);

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
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
