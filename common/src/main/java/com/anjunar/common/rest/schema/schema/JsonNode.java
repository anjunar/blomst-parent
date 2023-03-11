package com.anjunar.common.rest.schema.schema;

import com.anjunar.common.rest.schema.CategoryType;
import com.anjunar.common.rest.schema.validators.Validator;
import com.fasterxml.jackson.annotation.*;
import com.anjunar.common.rest.api.Link;

import java.util.LinkedHashMap;
import java.util.Set;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(JsonString.class),
        @JsonSubTypes.Type(JsonInteger.class),
        @JsonSubTypes.Type(JsonDouble.class),
        @JsonSubTypes.Type(JsonFloat.class),
        @JsonSubTypes.Type(JsonObject.class),
        @JsonSubTypes.Type(JsonArray.class),
        @JsonSubTypes.Type(JsonBoolean.class),
        @JsonSubTypes.Type(JsonNull.class),
        @JsonSubTypes.Type(JsonImage.class),
        @JsonSubTypes.Type(JsonEnum.class),
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
        LAZY_SELECT_NAME("lazy-select-name"),
        TEXTAREA("textarea"),
        EDITOR("editor"),

        LIKE("like"),
        JSON("json"),
        REFERENCE("reference");

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

    private Widget widget;

    private Boolean naming;

    private Boolean id;

    private Boolean readOnly;

    private Boolean dirty;

    private Boolean visible;

    private Boolean visibility;

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

    public Boolean getDirty() {
        return dirty;
    }

    public void setDirty(Boolean dirty) {
        this.dirty = dirty;
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

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }
}
