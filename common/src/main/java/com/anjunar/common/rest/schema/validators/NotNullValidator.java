package com.anjunar.common.rest.schema.validators;

public class NotNullValidator implements Validator {
    @Override
    public String getName() {
        return "notNull";
    }
}
