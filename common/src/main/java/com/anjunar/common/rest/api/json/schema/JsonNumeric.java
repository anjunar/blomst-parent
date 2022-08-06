package com.anjunar.common.rest.api.json.schema;

public abstract class JsonNumeric extends JsonNode {

    private Integer multiplyOf;

    private Integer maximum;
    private Integer minimum;

    public Integer getMultiplyOf() {
        return multiplyOf;
    }

    public void setMultiplyOf(Integer multiplyOf) {
        this.multiplyOf = multiplyOf;
    }

    public Integer getMaximum() {
        return maximum;
    }

    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }

    public Integer getMinimum() {
        return minimum;
    }

    public void setMinimum(Integer minimum) {
        this.minimum = minimum;
    }

}
