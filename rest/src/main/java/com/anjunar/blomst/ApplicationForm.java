package com.anjunar.blomst;

import com.anjunar.blomst.control.users.user.UserForm;
import com.anjunar.blomst.control.users.user.UserSelect;
import com.anjunar.common.rest.mapper.annotations.MapperView;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.anjunar.common.rest.api.Link;
import com.anjunar.common.rest.api.LinksContainer;
import com.anjunar.common.rest.schema.JsonSchemaGenerator;
import com.anjunar.common.rest.schema.schema.JsonObject;

public class ApplicationForm implements LinksContainer {

    @MapperView(UserSelect.class)
    private UserForm user;

    @JsonProperty(value = "$schema", access = JsonProperty.Access.READ_ONLY)
    private final JsonObject schema = JsonSchemaGenerator.generateObject(ApplicationForm.class);

    public UserForm getUser() {
        return user;
    }

    public void setUser(UserForm user) {
        this.user = user;
    }

    public JsonObject getSchema() {
        return schema;
    }

    @Override
    public void addLink(String rel, Link link) {
        schema.getLinks().put(rel, link);
    }
}
