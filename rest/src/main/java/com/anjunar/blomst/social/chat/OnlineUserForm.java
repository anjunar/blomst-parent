package com.anjunar.blomst.social.chat;

import com.anjunar.blomst.control.users.user.ImageConverter;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.MediaType;
import com.anjunar.common.rest.mapper.annotations.MapperConverter;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonSchema(widget = JsonNode.Widget.FORM)
@JsonTypeName("User")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true)
public class OnlineUserForm extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "First Name", naming = true)
    private String firstName;

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Last Name", naming = true)
    private String lastName;

    @JsonSchema(widget = JsonNode.Widget.CHECKBOX, title = "Online", readOnly = true)
    private Boolean online;

    @JsonSchema(widget = JsonNode.Widget.IMAGE, title = "Picture")
    @MapperConverter(ImageConverter.class)
    private MediaType picture;

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

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public MediaType getPicture() {
        return picture;
    }

    public void setPicture(MediaType picture) {
        this.picture = picture;
    }

}
