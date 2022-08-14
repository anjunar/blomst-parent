package com.anjunar.common.rest.schema.validators;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("maxValidator")
public class MaxValidator implements Validator {
    private final long value;

    @JsonCreator
    public MaxValidator(@JsonProperty("value") long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    @Override
    public String getName() {
        return "max";
    }
}
