package com.anjunar.common.rest.schema.factories;

import com.anjunar.common.rest.schema.validators.DecimalMaxValidator;
import com.anjunar.common.rest.schema.validators.DecimalMinValidator;
import com.anjunar.common.rest.schema.validators.DigitsValidator;
import com.google.common.reflect.TypeToken;
import com.anjunar.common.rest.schema.schema.JsonString;
import com.anjunar.introspector.bean.BeanProperty;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import java.math.BigDecimal;

public class BigDecimalFactory extends JsonAbstractFactory<JsonString> {

    @Override
    public boolean test(TypeToken<?> typeToken) {
        return typeToken.getRawType().equals(BigDecimal.class);
    }

    @Override
    public JsonString build(TypeToken<?> typeToken) {
        JsonString jsonString = new JsonString();
        jsonString.setFormat(JsonString.Format.BIG_DECIMAL);
        return jsonString;
    }

    @Override
    public JsonString buildWithAnnotation(BeanProperty<?, ?> property) {
        JsonString jsonString = super.buildWithAnnotation(property);

        DecimalMax decimalMax = property.getAnnotation(DecimalMax.class);
        if (decimalMax != null) {
            jsonString.addValidator("decimalMax", new DecimalMaxValidator(decimalMax.value()));
        }

        DecimalMin decimalMin = property.getAnnotation(DecimalMin.class);
        if (decimalMin != null) {
            jsonString.addValidator("decimalMin", new DecimalMinValidator(decimalMin.value()));
        }

        Digits digits = property.getAnnotation(Digits.class);
        if (digits != null) {
            jsonString.addValidator("digits", new DigitsValidator(digits.integer(), digits.fraction()));
        }

        return jsonString;
    }
}
