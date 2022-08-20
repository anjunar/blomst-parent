package com.anjunar.common.rest.schema.validators;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("digitsValidator")
public class DigitsValidator implements Validator {

    private final int integer;

    private final int fraction;

    @JsonCreator
    public DigitsValidator(@JsonProperty("integer") int integer,@JsonProperty("fraction") int fraction) {
        this.integer = integer;
        this.fraction = fraction;
    }

    public int getInteger() {
        return integer;
    }

    public int getFraction() {
        return fraction;
    }

}
