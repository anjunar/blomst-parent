package com.anjunar.common.ddd;

import com.anjunar.common.i18n.Translations;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;

@Converter(autoApply = true)
public class JsonConverter implements AttributeConverter<Translations, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Translations attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Translations convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readerFor(Translations.class)
                    .readValue(dbData, Translations.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
