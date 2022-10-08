package com.anjunar.blomst.shared.users.user;

import com.anjunar.blomst.system.languages.language.LanguageForm;
import com.anjunar.common.rest.mapper.annotations.MapperVisibility;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.google.common.base.Strings;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class UserSelect extends IdentitySelect {

    @Size(min = 3, max = 80)
    @NotBlank
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Nick Name", readOnly = true)
    private String nickName;

    @Size(min = 3, max = 80)
    @NotBlank
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "First Name", readOnly = true)
    @MapperVisibility
    private String firstName;

    @Size(min = 3, max = 80)
    @NotBlank
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Last Name", readOnly = true)
    @MapperVisibility
    private String lastName;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.DATE, title = "Birthdate", readOnly = true)
    @MapperVisibility
    private LocalDate birthDate;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Language", readOnly = true)
    private LanguageForm language;

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

    public LanguageForm getLanguage() {
        return language;
    }

    public void setLanguage(LanguageForm language) {
        this.language = language;
    }

    public String getName() {
        if (! Strings.isNullOrEmpty(firstName) && ! Strings.isNullOrEmpty(lastName)) {
            return firstName + " " + lastName;
        }
        return nickName;
    }

}
