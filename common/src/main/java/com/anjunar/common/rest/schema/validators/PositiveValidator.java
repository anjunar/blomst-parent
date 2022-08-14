package com.anjunar.common.rest.schema.validators;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("positiveValidator")
public class PositiveValidator implements Validator {
    @Override
    public String getName() {
        return "positive";
    }
}
