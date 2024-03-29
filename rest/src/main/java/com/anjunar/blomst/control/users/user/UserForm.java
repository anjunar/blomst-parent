package com.anjunar.blomst.control.users.user;

import com.anjunar.blomst.control.roles.role.RoleForm;
import com.anjunar.blomst.system.languages.language.LanguageForm;
import com.anjunar.common.filedisk.MediaDataConverter;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.MediaType;
import com.anjunar.common.rest.mapper.annotations.MapperConverter;
import com.anjunar.common.rest.mapper.annotations.MapperSecurity;
import com.anjunar.common.rest.mapper.annotations.MapperVisibility;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.common.base.Strings;
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
@JsonTypeName("User")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true)
public class UserForm extends AbstractRestEntity {

    @NotBlank
    @Size(min = 3, max = 80)
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Nick Name")
    private String nickName;

    @Size(min = 3, max = 80)
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "First Name", naming = true)
    @MapperVisibility
    private String firstName;

    @Size(min = 3, max = 80)
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Last Name", naming = true)
    @MapperVisibility
    private String lastName;

    @JsonSchema(widget = JsonNode.Widget.DATE, title = "Birthdate")
    @MapperVisibility
    private LocalDate birthDate;

    @MapperSecurity(rolesAllowed = {"Administrator"})
    @JsonSchema(widget = JsonNode.Widget.PASSWORD, title = "Password")
    private String password;

    @JsonSchema(widget = JsonNode.Widget.IMAGE, title = "Picture")
    @MapperConverter(MediaDataConverter.class)
    private MediaType picture = new MediaType();

    @JsonSchema(widget = JsonNode.Widget.IMAGE, title = "Background")
    @MapperConverter(MediaDataConverter.class)
    private MediaType background = new MediaType();

    @JsonSchema(widget = JsonNode.Widget.REPEAT, title = "Emails")
    @MapperVisibility
    private List<EmailForm> emails = new ArrayList<>();

    @JsonSchema(widget = JsonNode.Widget.CHECKBOX, title = "Enabled")
    @MapperSecurity(rolesAllowed = {"Administrator"})
    private boolean enabled;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Language")
    private LanguageForm language;

    @JsonSchema(widget = JsonNode.Widget.LAZY_MULTI_SELECT, title = "Roles")
    @Size(min = 1)
    @MapperSecurity(rolesAllowed = {"Administrator"})
    private Set<RoleForm> roles;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

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

    public MediaType getPicture() {
        return picture;
    }

    public void setPicture(MediaType picture) {
        this.picture = picture;
    }

    public MediaType getBackground() {
        return background;
    }

    public void setBackground(MediaType background) {
        this.background = background;
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

    public String getName() {
        if (! Strings.isNullOrEmpty(firstName) && ! Strings.isNullOrEmpty(lastName)) {
            return firstName + " " + lastName;
        }
        if (! Strings.isNullOrEmpty(nickName)) {
            return nickName;
        }
        return "";
    }

}
