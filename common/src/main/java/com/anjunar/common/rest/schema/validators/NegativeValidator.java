package com.anjunar.common.rest.schema.validators;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("negativeValidator")
public class NegativeValidator implements Validator {
    @Override
    public String getName() {
        return "negative";
    }
}
