package com.anjunar.blomst.social.pages;

import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.AbstractRestEntityConverter;
import com.anjunar.common.rest.api.json.annotations.JsonSchema;
import com.anjunar.common.rest.api.json.schema.JsonNode;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.shared.system.Language;
import com.anjunar.blomst.shared.users.user.UserSelect;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    private Language language;

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

    private static class PagesFormConverter extends AbstractRestEntityConverter<Page, PagesForm> {

        public static PagesFormConverter INSTANCE = new PagesFormConverter();

        public PagesForm factory(Page page, PagesForm resource) {
            resource.setTitle(page.getTitle());
            resource.setText(page.getText());
            resource.setLanguage(Language.factory(page.getLanguage()));
            resource.setModifier(UserSelect.factory(page.getModifier()));
            return super.factory(resource, page);
        }

        @Override
        public Page updater(PagesForm restEntity, Page entity, EntityManager entityManager, IdentityProvider identityProvider) {
            return null;
        }
    }

    public static PagesForm factory(Page page) {
        return PagesFormConverter.INSTANCE.factory(page, new PagesForm());
    }
}
