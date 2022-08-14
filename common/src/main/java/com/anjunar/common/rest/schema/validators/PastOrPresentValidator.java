package com.anjunar.common.rest.schema.validators;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("pastOrPresentValidator")
public class PastOrPresentValidator implements Validator {
    @Override
    public String getName() {
        return "pastOrPresent";
    }
}
