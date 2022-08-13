package com.anjunar.common.rest.schema.schema;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonTypeName("string")
public class JsonString extends JsonNode {

    public enum Format {

        NOOP("noop"),
        DATE_TIME("date-time"),
        TIME("time"),
        DATE("date"),
        DURATION("duration"),
        EMAIL("email"),
        IDN_EMAIL("idn-email"),
        HOSTNAME("hostname"),
        IDN_HOSTNAME("idn-hostname"),
        IP4("ip4"),
        IP6("ip6"),
        UUID("uuid"),
        URI("uri"),
        URI_REFERENCE("uri-reference"),
        IRI("iri"),
        IRI_REFERENCE("iri-reference"),
        URI_TEMPLATE("uri-template"),
        JSON_POINTER("json-pointer"),
        RELATIVE_JSON_POINTER("relative-json-pointer"),
        REGEX("regex"),

        BIG_INTEGER("big-integer"),
        BIG_DECIMAL("big-decimal");

        final String value;

        Format(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @JsonCreator
        public static Format fromString(String value) {
            for (Format format : values()) {
                if (value.equals(format.value)) {
                    return format;
                }
            }
            return null;
        }

    }

    private Integer minLength;
    private Integer maxLength;

    private String pattern;

    private Format format;

    private String contentMediaType;

    private String contentEncoding;

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public String getContentMediaType() {
        return contentMediaType;
    }

    public void setContentMediaType(String contentMediaType) {
        this.contentMediaType = contentMediaType;
    }

    public String getContentEncoding() {
        return contentEncoding;
    }

    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
    }
}
