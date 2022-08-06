package com.anjunar.common.rest.api.json.validators;

public class FutureOrPresentValidator implements Validator {
    @Override
    public String getName() {
        return "futureOrPresent";
    }
}
