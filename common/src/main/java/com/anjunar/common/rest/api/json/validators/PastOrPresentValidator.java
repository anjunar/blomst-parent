package com.anjunar.common.rest.api.json.validators;

public class PastOrPresentValidator implements Validator {
    @Override
    public String getName() {
        return "pastOrPresent";
    }
}
