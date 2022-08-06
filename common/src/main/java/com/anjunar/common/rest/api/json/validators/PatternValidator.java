package com.anjunar.common.rest.api.json.validators;

public class PatternValidator implements Validator {

    private final String regexp;

    public PatternValidator(String regexp) {
        this.regexp = regexp;
    }

    public String getRegexp() {
        return regexp;
    }

    @Override
    public String getName() {
        return "pattern";
    }
}
