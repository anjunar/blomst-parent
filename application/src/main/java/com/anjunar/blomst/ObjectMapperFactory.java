package com.anjunar.blomst;

import com.anjunar.blomst.fasterxml.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.TimeZone;

@ApplicationScoped
public class ObjectMapperFactory {

    @Produces
    private ObjectMapper objectMapperFactory() {
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setTimeZone(TimeZone.getDefault());

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(Locale.class, new LocaleDeserializer());
        simpleModule.addSerializer(Locale.class, new LocaleSerializer());

        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LocalDateTime.class, new UTCLocalDateTimeSerializer());
        module.addDeserializer(LocalDateTime.class, new UTCLocalDateTimeDeserializer());
        module.addSerializer(Instant.class, new UTCInstantSerializer());
        module.addDeserializer(Instant.class, new UTCInstantDeserializer());

        objectMapper.registerModule(simpleModule);
        objectMapper.registerModule(module);

        return objectMapper;
    }

}
