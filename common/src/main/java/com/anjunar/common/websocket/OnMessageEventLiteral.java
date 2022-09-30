package com.anjunar.common.websocket;

import jakarta.enterprise.util.AnnotationLiteral;

public class OnMessageEventLiteral extends AnnotationLiteral<OnMessageEvent> implements OnMessageEvent{

    private final String value;

    public OnMessageEventLiteral(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

}
