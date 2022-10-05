package com.anjunar.blomst.security.login;

import com.anjunar.common.rest.api.AbstractSchemaEntity;
import com.anjunar.common.rest.api.LinksContainer;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class LoginForm extends AbstractSchemaEntity implements LinksContainer {

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Email")
    @NotBlank
    @Email
    private String email = "";

    @JsonSchema(widget = JsonNode.Widget.PASSWORD, title = "Password")
    @Size(min = 3)
    @NotBlank
    private String password = "";

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

}
