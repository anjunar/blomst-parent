package com.anjunar.common.rest.api.json.factories;

import com.anjunar.common.rest.api.json.validators.*;
import com.anjunar.common.rest.api.json.schema.JsonNumeric;
import com.anjunar.introspector.bean.BeanProperty;

import jakarta.validation.constraints.*;

public abstract class NumericFactory<J extends JsonNumeric> extends JsonAbstractFactory<J> {

    @Override
    public J buildWithAnnotation(BeanProperty<?, ?> property) {
        J jsonNumber = super.buildWithAnnotation(property);

        Min min = property.getAnnotation(Min.class);
        if (min != null) {
            jsonNumber.addValidator("min", new MinValidator(min.value()));
        }

        Max max = property.getAnnotation(Max.class);
        if (max != null) {
            jsonNumber.addValidator("max", new MaxValidator(max.value()));
        }

        Negative negative = property.getAnnotation(Negative.class);
        if (negative != null) {
            jsonNumber.addValidator("negative", new NegativeValidator());
        }

        Positive positive = property.getAnnotation(Positive.class);
        if (positive != null) {
            jsonNumber.addValidator("positive", new PositiveValidator());
        }

        NegativeOrZero negativeOrZero = property.getAnnotation(NegativeOrZero.class);
        if (negativeOrZero != null) {
            jsonNumber.addValidator("negativeOrZero", new NegativeOrZeroValidator());
        }

        PositiveOrZero positiveOrZero = property.getAnnotation(PositiveOrZero.class);
        if (positiveOrZero != null) {
            jsonNumber.addValidator("positiveOrZero", new PositiveOrZeroValidator());
        }

        return jsonNumber;
    }
}
