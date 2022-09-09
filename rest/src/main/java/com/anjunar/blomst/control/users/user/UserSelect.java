package com.anjunar.blomst.control.users.user;

import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.annotations.JsonSchemaReadOnly;

import java.util.UUID;

public interface UserSelect {

    @JsonSchemaReadOnly
    UUID getId();

    @JsonSchemaReadOnly
    String getFirstName();

    void setFirstName(String firstName);

    @JsonSchemaReadOnly
    String getLastName();

    void setLastName(String lastName);
}
