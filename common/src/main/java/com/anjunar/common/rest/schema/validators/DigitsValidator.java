package com.anjunar.common.rest.schema.validators;

public class DigitsValidator implements Validator {

    private final int integer;

    private final int fraction;

    public DigitsValidator(int integer, int fraction) {
        this.integer = integer;
        this.fraction = fraction;
    }

    public int getInteger() {
        return integer;
    }

    public int getFraction() {
        return fraction;
    }

    @Override
    public String getName() {
        return "digits";
    }
}
