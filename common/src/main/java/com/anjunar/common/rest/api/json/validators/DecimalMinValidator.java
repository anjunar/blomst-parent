package com.anjunar.common.rest.api.json.validators;

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