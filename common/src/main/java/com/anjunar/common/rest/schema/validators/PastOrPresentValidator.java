package com.anjunar.common.rest.schema.validators;

public class PastOrPresentValidator implements Validator {
    @Override
    public String getName() {
        return "pastOrPresent";
    }
}
