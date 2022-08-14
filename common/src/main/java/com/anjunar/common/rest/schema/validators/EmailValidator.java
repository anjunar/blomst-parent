package com.anjunar.common.rest.schema.validators;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("emailValidator")
public class EmailValidator implements Validator {
    @Override
    public String getName() {
        return "email";
    }
}
