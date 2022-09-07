package com.anjunar.blomst.system.languages.language;

import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;

import java.util.Locale;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class LanguageForm extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Locale")
    private Locale locale;

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Name", naming = true)
    private String name;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
