package com.anjunar.common.rest.api.json.validators;

public class NegativeOrZeroValidator implements Validator {
    @Override
    public String getName() {
        return "negativeOrZero";
    }
}
