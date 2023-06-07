package com.anjunar.blomst.shared.users.user;

import com.anjunar.common.filedisk.MediaURLConverter;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.MediaType;
import com.anjunar.common.rest.mapper.annotations.MapperConverter;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserSelect.class, name = "User")
})
public class IdentitySelect extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Name", readOnly = true, naming = true)
    private String name;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.IMAGE, title = "Picture", readOnly = true)
    @MapperConverter(MediaURLConverter.class)
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
