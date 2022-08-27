package com.anjunar.blomst.social.pages.page;

import com.anjunar.common.rest.api.Editor;
import com.anjunar.common.rest.objectmapper.MapperConverter;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.validators.Dom;
import com.anjunar.blomst.shared.likeable.AbstractLikeableRestEntity;
import com.anjunar.blomst.shared.system.Language;
import com.anjunar.blomst.shared.users.user.UserSelect;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class PageForm extends AbstractLikeableRestEntity {

    @Size(min = 3, max = 80)
    @NotBlank
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Title",naming = true)
    private String title;

    @JsonSchema(widget = JsonNode.Widget.EDITOR, title = "Editor")
    @Dom
    @NotNull
    private Editor editor = new Editor();

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Language")
    @MapperConverter(LanguageConverter.class)
    private Language language;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Modifier")
    private UserSelect modifier;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public UserSelect getModifier() {
        return modifier;
    }

    public void setModifier(UserSelect modifier) {
        this.modifier = modifier;
    }

}
