package com.anjunar.common.rest.schema.validators;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("notNullValidator")
public class NotNullValidator implements Validator {
    @Override
    public String getName() {
        return "notNull";
    }
}
