package com.anjunar.blomst.jaxrs;

import com.anjunar.common.rest.api.DateDuration;
import com.anjunar.common.rest.api.DateTimeDuration;

import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Locale;

@Provider
public class CustomParamConverterProvider implements ParamConverterProvider {
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType.equals(Locale.class)) {
            return (ParamConverter<T>) new LocalParamConverter();
        }
        if (rawType.equals(DateTimeDuration.class)) {
            return (ParamConverter<T>) new DateTimeDurationConverter();
        }
        if (rawType.equals(DateDuration.class)) {
            return (ParamConverter<T>) new DateDurationConverter();
        }
        return null;
    }
}
