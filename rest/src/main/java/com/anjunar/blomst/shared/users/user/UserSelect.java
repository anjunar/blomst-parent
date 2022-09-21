package com.anjunar.blomst.shared.users.user;

import com.anjunar.blomst.control.users.user.ImageConverter;
import com.anjunar.blomst.system.languages.language.LanguageForm;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.ImageType;
import com.anjunar.common.rest.mapper.annotations.MapperConverter;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class UserSelect extends IdentitySelect {

    @Size(min = 3, max = 80)
    @NotBlank
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "First Name", readOnly = true)
    private String firstName;

    @Size(min = 3, max = 80)
    @NotBlank
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Last Name", readOnly = true)
    private String lastName;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.DATE, title = "Birthdate", readOnly = true)
    private LocalDate birthDate;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Language", readOnly = true)
    private LanguageForm language;

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

}
