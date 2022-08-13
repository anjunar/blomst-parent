package com.anjunar.common.rest.schema.validators;

public class PositiveOrZeroValidator implements Validator {
    @Override
    public String getName() {
        return "positiveOrZero";
    }
}
