package com.anjunar.common.rest.link;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum LinkType {

    TABLE("table"),
    FORM("form");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }


    LinkType(String value) {
        this.value = value;
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
