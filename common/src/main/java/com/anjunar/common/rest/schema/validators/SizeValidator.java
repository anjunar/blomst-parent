package com.anjunar.common.rest.schema.validators;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("sizeValidator")
public class SizeValidator implements Validator {

    private final int min;
    private final int max;

    @JsonCreator
    public SizeValidator(@JsonProperty("min") int min,@JsonProperty("max") int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    @Override
    public String getName() {
        return "size";
    }

}
