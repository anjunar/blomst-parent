package com.anjunar.common.rest.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum LinkType {

    ACTION("action"),
    LINK("link"),
    SOURCE("source"),
    REDIRECT("redirect");

    private final String value;

    LinkType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static LinkType fromString(String value) {
        for (LinkType format : values()) {
            if (value.equals(format.value)) {
                return format;
            }
        }
        return null;
    }

}
