package com.anjunar.blomst.social.pages;

import com.anjunar.blomst.control.users.user.UserForm;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.system.languages.language.LanguageForm;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.mapper.annotations.MapperView;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class PagesForm extends AbstractRestEntity {

    @Size(min = 3, max = 80)
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Title", naming = true)
    private String title;

    @NotBlank
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Text")
    private String text;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Language")
    private LanguageForm language;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Modfier")
    private UserSelect modifier;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LanguageForm getLanguage() {
        return language;
    }

    public void setLanguage(LanguageForm language) {
        this.language = language;
    }

    public UserSelect getModifier() {
        return modifier;
    }

    public void setModifier(UserSelect modifier) {
        this.modifier = modifier;
    }

}
