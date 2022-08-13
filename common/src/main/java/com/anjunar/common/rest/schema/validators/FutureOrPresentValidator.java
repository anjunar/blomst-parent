package com.anjunar.common.rest.schema.validators;

public class FutureOrPresentValidator implements Validator {
    @Override
    public String getName() {
        return "futureOrPresent";
    }
}
