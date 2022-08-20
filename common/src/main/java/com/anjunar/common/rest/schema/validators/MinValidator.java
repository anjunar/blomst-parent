package com.anjunar.common.rest.schema.validators;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("minValidator")
public class MinValidator implements Validator {

    private final long value;

    @JsonCreator
    public MinValidator(@JsonProperty("value") long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

}
