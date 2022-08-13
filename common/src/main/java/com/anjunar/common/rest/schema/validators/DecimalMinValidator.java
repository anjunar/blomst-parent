package com.anjunar.common.rest.schema.validators;

public class DecimalMinValidator implements Validator {

    private final String value;

    public DecimalMinValidator(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String getName() {
        return "decimalMin";
    }
}
