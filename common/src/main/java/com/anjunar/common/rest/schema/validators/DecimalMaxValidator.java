package com.anjunar.common.rest.schema.validators;

public class DecimalMaxValidator implements Validator {

    private final String value;

    public DecimalMaxValidator(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String getName() {
        return "decimalMax";
    }
}
