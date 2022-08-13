package com.anjunar.blomst.shared.system;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.anjunar.common.rest.schema.JsonSchemaGenerator;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.rest.schema.schema.JsonObject;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Locale;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class Language {

    @JsonSchema(widget = JsonNode.Widget.TEXT, naming = true, title = "Language")
    @Size(min = 3, max = 80)
    @NotBlank
    private String language;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Locale", id = true)
    private Locale locale;

    @JsonSchema(ignore = true)
    @JsonProperty(value = "$schema", access = JsonProperty.Access.READ_ONLY)
    private final JsonObject schema = JsonSchemaGenerator.generateObject(Language.class);

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public JsonObject getSchema() {
        return schema;
    }

    public static Language factory(Locale locale) {
        if (locale == null) {
            return null;
        }

        Language language = new Language();
        if (locale.toLanguageTag().equals("de-DE")) {
            language.setLanguage("Deutsch");
            language.setLocale(locale);
        }
        if (locale.toLanguageTag().equals("en-DE")) {
            language.setLanguage("English");
            language.setLocale(locale);
        }
        return language;
    }

    public static Locale updater(Language language) {
        return language.getLocale();
    }

}
