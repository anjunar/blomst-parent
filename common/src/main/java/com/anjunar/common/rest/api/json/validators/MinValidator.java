package com.anjunar.common.rest.api.json.validators;

public class MinValidator implements Validator {

    private final long value;

    public MinValidator(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    @Override
    public String getName() {
        return "min";
    }

}
