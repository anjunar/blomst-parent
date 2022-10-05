package com.anjunar.blomst.control.users.user;

import com.anjunar.blomst.control.roles.role.RoleForm;
import com.anjunar.blomst.system.languages.language.LanguageForm;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.ImageType;
import com.anjunar.common.rest.mapper.annotations.MapperConverter;
import com.anjunar.common.rest.mapper.annotations.MapperSecurity;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NaturalId
@JsonSchema(widget = JsonNode.Widget.FORM)
public class UserForm extends AbstractRestEntity {

    @NotBlank
    @Size(min = 3, max = 80)
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "First Name", naming = true)
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 80)
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Last Name", naming = true)
    private String lastName;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.DATE, title = "Birthdate")
    private LocalDate birthDate;

    @MapperSecurity(rolesAllowed = {"Administrator"})
    @JsonSchema(widget = JsonNode.Widget.PASSWORD, title = "Password")
    private String password;

    @JsonSchema(widget = JsonNode.Widget.IMAGE, title = "Picture")
    @MapperConverter(ImageConverter.class)
    private ImageType picture = new ImageType();

    @JsonSchema(widget = JsonNode.Widget.REPEAT, title = "Emails")
    private List<EmailForm> emails = new ArrayList<>();

    @JsonSchema(widget = JsonNode.Widget.CHECKBOX, title = "Enabled")
    @MapperSecurity(rolesAllowed = {"Administrator"})
    private boolean enabled;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Language")
    private LanguageForm language;

    @JsonSchema(widget = JsonNode.Widget.LAZY_MULTI_SELECT, title = "Roles")
    @Size(min = 1)
    @NotNull
    @MapperSecurity(rolesAllowed = {"Administrator"})
    private Set<RoleForm> roles = new HashSet<>();

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

    public ImageType getPicture() {
        return picture;
    }

    public void setPicture(ImageType picture) {
        this.picture = picture;
    }

    public List<EmailForm> getEmails() {
        return emails;
    }

    public void setEmails(List<EmailForm> emails) {
        this.emails = emails;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public LanguageForm getLanguage() {
        return language;
    }

    public void setLanguage(LanguageForm language) {
        this.language = language;
    }

    public Set<RoleForm> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleForm> roles) {
        this.roles = roles;
    }

}
