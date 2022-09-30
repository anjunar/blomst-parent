package com.anjunar.common.websocket;

import jakarta.enterprise.util.AnnotationLiteral;

public class OnOpenEventLiteral extends AnnotationLiteral<OnOpenEvent> {

    private final String value;

    public OnOpenEventLiteral(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
