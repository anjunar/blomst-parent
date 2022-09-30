package com.anjunar.common.websocket;

import jakarta.enterprise.util.AnnotationLiteral;

public class OnCloseEventLiteral extends AnnotationLiteral<OnCloseEvent> {
    private final String value;

    public OnCloseEventLiteral(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
