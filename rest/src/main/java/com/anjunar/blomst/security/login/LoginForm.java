package com.anjunar.blomst.security.login;

import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonSchema(widget = JsonNode.Widget.FORM)
@JsonTypeName("User")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true)
public class LoginForm {

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Email")
    @NotBlank
    @Email
    private String email = "";

    @JsonSchema(widget = JsonNode.Widget.PASSWORD, title = "Password")
    @Size(min = 3, max = 80)
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
