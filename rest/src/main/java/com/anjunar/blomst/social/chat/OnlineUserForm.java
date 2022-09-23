package com.anjunar.blomst.social.chat;

import com.anjunar.blomst.control.users.user.ImageConverter;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.ImageType;
import com.anjunar.common.rest.mapper.annotations.MapperConverter;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class OnlineUserForm extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "First Name", naming = true)
    private String firstName;

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Last Name", naming = true)
    private String lastName;

    @JsonSchema(widget = JsonNode.Widget.IMAGE, title = "Picture")
    @MapperConverter(ImageConverter.class)
    private ImageType picture;

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

    public ImageType getPicture() {
        return picture;
    }

    public void setPicture(ImageType picture) {
        this.picture = picture;
    }

}
