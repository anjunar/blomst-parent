package com.anjunar.blomst;

import com.anjunar.blomst.fasterxml.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Patrick Bittner on 25.05.2015.
 */
@Provider
@Consumes("application/json")
@Produces({"application/json"})
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {

    @Override
    public ObjectMapper getContext(Class<?> type) {
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

