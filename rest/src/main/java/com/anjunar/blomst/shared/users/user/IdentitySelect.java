package com.anjunar.blomst.shared.users.user;

import com.anjunar.blomst.control.users.user.ImageConverter;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.ImageType;
import com.anjunar.common.rest.mapper.annotations.MapperConverter;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import jakarta.validation.constraints.NotNull;

public class IdentitySelect extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Name", readOnly = true, naming = true)
    private String name;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.IMAGE, title = "Picture", readOnly = true)
    @MapperConverter(ImageConverter.class)
    private ImageType picture;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageType getPicture() {
        return picture;
    }

    public void setPicture(ImageType picture) {
        this.picture = picture;
    }
}
