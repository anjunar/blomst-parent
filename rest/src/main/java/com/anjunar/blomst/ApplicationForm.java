package com.anjunar.blomst;

import com.anjunar.blomst.shared.users.user.UserSelect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.anjunar.common.rest.api.Link;
import com.anjunar.common.rest.api.LinksContainer;
import com.anjunar.common.rest.api.json.JsonSchemaGenerator;
import com.anjunar.common.rest.api.json.schema.JsonObject;

public class ApplicationForm implements LinksContainer {

    private UserSelect user;

    @JsonProperty(value = "$schema", access = JsonProperty.Access.READ_ONLY)
    private final JsonObject schema = JsonSchemaGenerator.generateObject(ApplicationForm.class);

    public UserSelect getUser() {
        return user;
    }

    public void setUser(UserSelect user) {
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