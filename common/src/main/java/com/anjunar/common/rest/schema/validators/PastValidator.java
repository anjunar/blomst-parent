package com.anjunar.common.rest.schema.validators;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("pastValidator")
public class PastValidator implements Validator {
    @Override
    public String getName() {
        return "past";
    }
}
