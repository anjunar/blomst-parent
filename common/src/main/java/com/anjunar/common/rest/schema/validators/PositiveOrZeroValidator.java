package com.anjunar.common.rest.schema.validators;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("positiveOrZeroValidator")
public class PositiveOrZeroValidator implements Validator {
    @Override
    public String getName() {
        return "positiveOrZero";
    }
}
