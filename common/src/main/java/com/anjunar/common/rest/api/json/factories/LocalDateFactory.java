package com.anjunar.common.rest.api.json.factories;

import com.anjunar.common.rest.api.json.validators.FutureOrPresentValidator;
import com.anjunar.common.rest.api.json.validators.FutureValidator;
import com.anjunar.common.rest.api.json.validators.PastOrPresentValidator;
import com.anjunar.common.rest.api.json.validators.PastValidator;
import com.google.common.reflect.TypeToken;
import com.anjunar.common.rest.api.json.schema.JsonString;
import de.anjunar.introspector.bean.BeanProperty;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@SuppressWarnings("UnstableApiUsage")
public class LocalDateFactory extends JsonAbstractFactory<JsonString> {
    @Override
    public boolean test(TypeToken<?> typeToken) {
        return typeToken.getRawType().equals(LocalDate.class);
    }

    @Override
    public JsonString build(TypeToken<?> typeToken) {
        JsonString jsonString = new JsonString();
        jsonString.setFormat(JsonString.Format.DATE);
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
