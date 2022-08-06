package com.anjunar.common.rest.api.json.schema;

import com.anjunar.common.rest.api.json.validators.Validator;
import com.fasterxml.jackson.annotation.*;
import com.anjunar.common.rest.api.Link;

import java.util.LinkedHashMap;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(JsonString.class),
        @JsonSubTypes.Type(JsonInteger.class),
        @JsonSubTypes.Type(JsonNumber.class),
        @JsonSubTypes.Type(JsonObject.class),
        @JsonSubTypes.Type(JsonArray.class),
        @JsonSubTypes.Type(JsonBoolean.class),
        @JsonSubTypes.Type(JsonNull.class),
        @JsonSubTypes.Type(JsonImage.class),
})
public abstract class JsonNode {

    public enum Widget {

        NO_WIDGET("no-widget"),
        CHECKBOX("checkbox"),
        COLOR("color"),
        DATE("date"),
        DATETIME_LOCAL("datetime-local"),
        EMAIL("email"),
        FILE("file"),
        HIDDEN("hidden"),
        IMAGE("image"),
        MONTH("month"),
        NUMBER("number"),
        PASSWORD("password"),
        RADIO("radio"),
        RANGE("range"),
        RESET("reset"),
        SEARCH("search"),
        SUBMIT("submit"),
        TEL("tel"),
        TEXT("text"),
        TIME("time"),
        URL("url"),
        WEEK("week"),

        SELECT("select"),

        FORM("form"),
        TABLE("table"),
        REPEAT("repeat"),
        LAZY_MULTI_SELECT("lazy-multi-select"),
        LAZY_SELECT("lazy-select"),
        TEXTAREA("textarea"),
        EDITOR("editor");

        final String value;

        Widget(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @JsonCreator
        public static Widget fromString(String value) {
            for (Widget format : values()) {
                if (value.equals(format.value)) {
                    return format;
                }
            }
            return null;
        }

    }

    private String title;

    private String description;

    private String name;

    private Widget widget;

    private Boolean naming;

    private Boolean id;

    private Boolean readOnly;

    private final LinkedHashMap<String, Link> links = new LinkedHashMap<>();

    private final LinkedHashMap<String, Validator> validators = new LinkedHashMap<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
    }

    public Boolean isId() {
        return id;
    }

    public void setId(Boolean id) {
        this.id = id;
    }

    public Boolean isNaming() {
        return naming;
    }

    public void setNaming(Boolean naming) {
        this.naming = naming;
    }

    public Boolean getNaming() {
        return naming;
    }

    public Boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public LinkedHashMap<String, Link> getLinks() {
        return links;
    }

    public LinkedHashMap<String, Validator> getValidators() {
        return validators;
    }

    public void addLink(String rel, Link link) {
        links.put(rel, link);
    }

    public void addValidator(String name, Validator validator) {
        validators.put(name, validator);
    }

}
