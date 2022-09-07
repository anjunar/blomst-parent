package com.anjunar.common.rest.schema.factories;

import com.anjunar.common.rest.schema.validators.FutureOrPresentValidator;
import com.anjunar.common.rest.schema.validators.FutureValidator;
import com.anjunar.common.rest.schema.validators.PastOrPresentValidator;
import com.anjunar.common.rest.schema.validators.PastValidator;
import com.google.common.reflect.TypeToken;
import com.anjunar.common.rest.schema.schema.JsonString;
import com.anjunar.introspector.bean.BeanProperty;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@SuppressWarnings("UnstableApiUsage")
public class LocalDateTimeFactory extends JsonAbstractFactory<JsonString> {
    @Override
    public boolean test(TypeToken<?> typeToken) {
        return typeToken.getRawType().equals(LocalDateTime.class);
    }

    @Override
    public JsonString build(TypeToken<?> typeToken, BeanProperty<?, ?> property) {
        JsonString jsonString = new JsonString();
        jsonString.setFormat(JsonString.Format.DATE_TIME);
        return jsonString;
    }

    @Override
    public JsonString buildWithAnnotation(BeanProperty<?, ?> property) {
        JsonString jsonString = super.buildWithAnnotation(property);

        Future future = property.getAnnotation(Future.class);
        if (future != null) {
            jsonString.addValidator("future", new FutureValidator());
        }

        Past past = property.getAnnotation(Past.class);
        if (past != null) {
            jsonString.addValidator("past", new PastValidator());
        }

        FutureOrPresent futureOrPresent = property.getAnnotation(FutureOrPresent.class);
        if (futureOrPresent != null) {
            jsonString.addValidator("futureOrPresent", new FutureOrPresentValidator());
        }

        PastOrPresent pastOrPresent = property.getAnnotation(PastOrPresent.class);
        if (pastOrPresent != null) {
            jsonString.addValidator("pastOrPresent", new PastOrPresentValidator());
        }

        return jsonString;
    }
}
