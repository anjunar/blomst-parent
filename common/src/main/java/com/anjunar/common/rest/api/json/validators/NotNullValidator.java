package com.anjunar.common.rest.api.json.validators;

public class NotNullValidator implements Validator {
    @Override
    public String getName() {
        return "notNull";
    }
}
