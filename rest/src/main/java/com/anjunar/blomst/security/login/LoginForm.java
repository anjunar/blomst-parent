package com.anjunar.blomst.security.login;

import com.anjunar.common.rest.api.AbstractSchemaEntity;
import com.anjunar.common.rest.api.LinksContainer;
import com.anjunar.common.rest.api.json.annotations.JsonSchema;
import com.anjunar.common.rest.api.json.schema.JsonNode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class LoginForm extends AbstractSchemaEntity implements LinksContainer {

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "First Name")
    @Size(min = 3, max = 80)
    @NotBlank
    private String firstname = "";

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Last Name")
    @Size(min = 3, max = 80)
    @NotBlank
    private String lastname = "";

    @JsonSchema(widget = JsonNode.Widget.DATE, title = "Birthdate")
    @NotNull
    private LocalDate birthday = LocalDate.now();

    @JsonSchema(widget = JsonNode.Widget.PASSWORD, title = "Password")
    @Size(min = 3)
    @NotBlank
    private String password = "";

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
