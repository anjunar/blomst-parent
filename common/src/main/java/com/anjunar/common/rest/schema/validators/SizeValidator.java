package com.anjunar.common.rest.schema.validators;

public class SizeValidator implements Validator {

    private final int min;
    private final int max;

    public SizeValidator(int min, int max) {
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
