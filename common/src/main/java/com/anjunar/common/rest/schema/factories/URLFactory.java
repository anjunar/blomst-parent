package com.anjunar.common.rest.schema.factories;

import com.anjunar.common.rest.schema.JsonContext;
import com.anjunar.introspector.bean.BeanProperty;
import com.google.common.reflect.TypeToken;
import com.anjunar.common.rest.schema.schema.JsonString;

import java.net.URL;

public class URLFactory extends JsonAbstractFactory<JsonString> {
    @Override
    public boolean test(TypeToken<?> typeToken) {
        return typeToken.getRawType().equals(URL.class);
    }

    @Override
    public JsonString build(TypeToken<?> typeToken, BeanProperty<?, ?> property, JsonContext context) {
        JsonString jsonString = new JsonString();

        jsonString.setFormat(JsonString.Format.URI);

        return jsonString;
    }

}
