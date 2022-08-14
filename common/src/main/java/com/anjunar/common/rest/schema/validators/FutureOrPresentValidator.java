package com.anjunar.common.rest.schema.validators;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("futureOrPresentValidator")
public class FutureOrPresentValidator implements Validator {
    @Override
    public String getName() {
        return "futureOrPresent";
    }
}
