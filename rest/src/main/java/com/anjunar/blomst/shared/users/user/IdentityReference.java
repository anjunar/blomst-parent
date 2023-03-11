package com.anjunar.blomst.shared.users.user;

import com.anjunar.common.rest.api.AbstractRestEntity;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserReference.class, name = "User")
})
public class IdentityReference extends AbstractRestEntity { }
