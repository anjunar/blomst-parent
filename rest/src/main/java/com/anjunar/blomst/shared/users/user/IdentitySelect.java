package com.anjunar.blomst.shared.users.user;

import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.MediaType;
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
    private MediaType picture;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MediaType getPicture() {
        return picture;
    }

    public void setPicture(MediaType picture) {
        this.picture = picture;
    }
}
