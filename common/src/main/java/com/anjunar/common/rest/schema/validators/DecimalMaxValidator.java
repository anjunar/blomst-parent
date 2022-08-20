package com.anjunar.common.rest.schema.validators;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("decimalMaxValidator")
public class DecimalMaxValidator implements Validator {

    private final String value;

    @JsonCreator
    public DecimalMaxValidator(@JsonProperty("value") String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
