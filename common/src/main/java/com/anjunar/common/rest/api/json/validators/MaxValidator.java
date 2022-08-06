package com.anjunar.common.rest.api.json.validators;

public class MaxValidator implements Validator {
    private final long value;

    public MaxValidator(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    @Override
    public String getName() {
        return "max";
    }
}
