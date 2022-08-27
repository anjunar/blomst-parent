package com.anjunar.blomst.shared.users.user;

import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.ImageType;
import com.anjunar.common.rest.objectmapper.MapperConverter;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Locale;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class UserSelect extends AbstractRestEntity {

    @Size(min = 3, max = 80)
    @NotBlank
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "First Name", naming = true, readOnly = true)
    private String firstName;

    @Size(min = 3, max = 80)
    @NotBlank
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Last Name", naming = true, readOnly = true)
    private String lastName;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.DATE, title = "Birthdate", readOnly = true)
    private LocalDate birthDate;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.IMAGE, title = "Picture", readOnly = true)
    @MapperConverter(ImageConverter.class)
    private ImageType picture;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Language", readOnly = true)
    private Locale language;

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

    public ImageType getPicture() {
        return picture;
    }

    public void setPicture(ImageType picture) {
        this.picture = picture;
    }

    public Locale getLanguage() {
        return language;
    }

    public void setLanguage(Locale language) {
        this.language = language;
    }

}
