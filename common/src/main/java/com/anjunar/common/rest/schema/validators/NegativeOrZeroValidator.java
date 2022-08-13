package com.anjunar.common.rest.schema.validators;

public class NegativeOrZeroValidator implements Validator {
    @Override
    public String getName() {
        return "negativeOrZero";
    }
}
