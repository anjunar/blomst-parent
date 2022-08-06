package com.anjunar.blomst.control.mail.template;

import com.anjunar.common.rest.api.json.annotations.JsonSchema;
import com.anjunar.common.rest.api.json.schema.JsonNode;
import com.anjunar.common.validators.Dom;
import com.anjunar.blomst.shared.system.Language;
import com.anjunar.common.mail.Template;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.AbstractRestEntityConverter;
import com.anjunar.common.rest.api.Editor;
import com.anjunar.common.security.IdentityProvider;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class TemplateForm extends AbstractRestEntity {

    @Size(min = 3, max = 80)
    @NotBlank
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Name")
    private String name;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Language")
    private Language language;

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

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Editor getContent() {
        return content;
    }

    public void setContent(Editor content) {
        this.content = content;
    }

    private static class TemplateFormConverter extends AbstractRestEntityConverter<Template, TemplateForm> {

        public static TemplateFormConverter INSTANCE = new TemplateFormConverter();

        public TemplateForm factory(TemplateForm resource, Template template) {
            super.factory(resource, template);
            resource.setId(template.getId());
            resource.setName(template.getName());
            resource.setLanguage(Language.factory(template.getLanguage()));
            resource.setContent(Editor.factory(template.getHtml(), template.getText()));
            return resource;
        }

        public Template updater(TemplateForm form, Template template, EntityManager entityManager, IdentityProvider identityProvider) {
            template.setName(form.getName());
            template.setLanguage(Language.updater(form.getLanguage()));
            template.setHtml(form.getContent().getHtml());
            template.setText(form.getContent().getText());
            return template;
        }
    }

    public static TemplateForm factory(Template template) {
        return TemplateFormConverter.INSTANCE.factory(new TemplateForm(), template);
    }

    public static void updater(TemplateForm form, Template template, EntityManager entityManager, IdentityProvider identityProvider) {
        TemplateFormConverter.INSTANCE.updater(form, template, entityManager, identityProvider);
    }

}
