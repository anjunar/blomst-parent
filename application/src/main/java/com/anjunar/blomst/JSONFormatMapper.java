package com.anjunar.blomst;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.type.FormatMapper;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;

import java.io.IOException;

public class JSONFormatMapper implements FormatMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T fromString(CharSequence charSequence, JavaType<T> javaType, WrapperOptions wrapperOptions) {
        final StringBuilder stringBuilder = new StringBuilder(charSequence.length());
        stringBuilder.append(charSequence);

        try {
            return objectMapper.readerFor(javaType.getJavaTypeClass())
                    .readValue(stringBuilder.toString(), javaType.getJavaTypeClass());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> String toString(T value, JavaType<T> javaType, WrapperOptions wrapperOptions) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
