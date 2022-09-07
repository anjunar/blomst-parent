package com.anjunar.common.rest.schema.factories;

import com.anjunar.common.rest.schema.validators.EmailValidator;
import com.anjunar.common.rest.schema.validators.NotBlankValidator;
import com.anjunar.common.rest.schema.validators.PatternValidator;
import com.anjunar.common.rest.schema.validators.SizeValidator;
import com.google.common.reflect.TypeToken;
import com.anjunar.common.rest.schema.schema.JsonString;
import com.anjunar.introspector.bean.BeanProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@SuppressWarnings("UnstableApiUsage")
public class StringFactory extends JsonAbstractFactory<JsonString> {
    @Override
    public boolean test(TypeToken<?> typeToken) {
        return typeToken.getRawType().equals(String.class);
    }

    @Override
    public JsonString build(TypeToken<?> typeToken, BeanProperty<?, ?> property) {
        return new JsonString();
    }

    @Override
    public JsonString buildWithAnnotation(BeanProperty<?, ?> property) {
        JsonString jsonString = super.buildWithAnnotation(property);

        Email email = property.getAnnotation(Email.class);
        if (email != null) {
            jsonString.setFormat(JsonString.Format.EMAIL);
            jsonString.addValidator("email", new EmailValidator());
        }

        Pattern pattern = property.getAnnotation(Pattern.class);
        if (pattern != null) {
            jsonString.setPattern(pattern.regexp());
            jsonString.addValidator("pattern", new PatternValidator(pattern.regexp()));
        }

        NotBlank notBlank = property.getAnnotation(NotBlank.class);
        if (notBlank != null) {
            jsonString.addValidator("notBlank", new NotBlankValidator());
        }

        Size size = property.getAnnotation(Size.class);
        if (size != null) {
            jsonString.addValidator("size", new SizeValidator(size.min(), size.max()));
        }

        return jsonString;
    }
}
