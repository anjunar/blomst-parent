package com.anjunar.common.rest.schema.validators;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("patternValidator")
public class PatternValidator implements Validator {

    private final String regexp;

    @JsonCreator
    public PatternValidator(@JsonProperty("regexp") String regexp) {
        this.regexp = regexp;
    }

    public String getRegexp() {
        return regexp;
    }

}
