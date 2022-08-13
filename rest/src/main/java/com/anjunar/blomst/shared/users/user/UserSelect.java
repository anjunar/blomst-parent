package com.anjunar.blomst.shared.users.user;

import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.ImageType;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.security.User;

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
    private ImageType image;

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

    public ImageType getImage() {
        return image;
    }

    public void setImage(ImageType image) {
        this.image = image;
    }

    public Locale getLanguage() {
        return language;
    }

    public void setLanguage(Locale language) {
        this.language = language;
    }

    public static UserSelect factory(User user) {
        UserSelect resource = new UserSelect();
        resource.setId(user.getId());
        resource.setFirstName(user.getFirstName());
        resource.setLastName(user.getLastName());
        resource.setBirthDate(user.getBirthDate());
        resource.setLanguage(user.getLanguage());
        resource.setImage(ImageType.factory(user.getPicture()));
        resource.setCreated(user.getCreated());
        resource.setModified(user.getModified());
        return resource;
    }

}
