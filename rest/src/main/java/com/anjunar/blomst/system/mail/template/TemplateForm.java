package com.anjunar.blomst.system.mail.template;

import com.anjunar.blomst.system.languages.language.LanguageForm;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.Editor;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.validators.Dom;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class TemplateForm extends AbstractRestEntity {

    @Size(min = 3, max = 80)
    @NotBlank
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Name")
    private String name;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Language")
    private LanguageForm language;

    @NotNull
    @Dom
    @JsonSchema(widget = JsonNode.Widget.EDITOR, title = "Editor")
    private Editor content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LanguageForm getLanguage() {
        return language;
    }

    public void setLanguage(LanguageForm language) {
        this.language = language;
    }

    public Editor getContent() {
        return content;
    }

    public void setContent(Editor content) {
        this.content = content;
    }

}
