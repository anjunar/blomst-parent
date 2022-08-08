package com.anjunar.blomst.social.pages.page;

import com.anjunar.common.rest.api.Editor;
import com.anjunar.common.rest.api.json.annotations.JsonSchema;
import com.anjunar.common.rest.api.json.schema.JsonNode;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.validators.Dom;
import com.anjunar.blomst.social.pages.Page;
import com.anjunar.blomst.shared.likeable.AbstractLikeableRestEntity;
import com.anjunar.blomst.shared.likeable.AbstractLikeableRestEntityConverter;
import com.anjunar.blomst.shared.system.Language;
import com.anjunar.blomst.shared.users.user.UserSelect;

import jakarta.persistence.EntityManager;
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
    private Editor content = new Editor();

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Language")
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

    public Editor getContent() {
        return content;
    }

    public void setContent(Editor content) {
        this.content = content;
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

    private static class PageFormConverter extends AbstractLikeableRestEntityConverter<Page, PageForm> {

        public static PageFormConverter INSTANCE = new PageFormConverter();

        public PageForm factory(PageForm pageForm, Page page) {
            pageForm.setId(page.getId());
            pageForm.setTitle(page.getTitle());
            pageForm.setContent(Editor.factory(page.getContent(), page.getText()));
            pageForm.setLanguage(Language.factory(page.getLanguage()));
            pageForm.setCreated(page.getCreated());
            pageForm.setModified(page.getModified());
            pageForm.setModifier(UserSelect.factory(page.getModifier()));
            return super.factory(pageForm, page);
        }

        @Override
        public Page updater(PageForm pageForm, Page page, EntityManager entityManager, IdentityProvider identityProvider) {
            page.setTitle(pageForm.getTitle());
            page.setContent(pageForm.getContent().getHtml());
            page.setText(pageForm.getContent().getText());
            page.setModifier(identityProvider.getUser());
            page.setLanguage(Language.updater(pageForm.getLanguage()));
            page.setModifier(identityProvider.getUser());
            page.getLinks().clear();
            return super.updater(pageForm, page, entityManager, identityProvider);
        }
    }

    public static PageForm factory(Page entity) {
        return PageFormConverter.INSTANCE.factory(new PageForm(), entity);
    }

    public static Page updater(PageForm form, Page entity, IdentityProvider identityProvider, EntityManager entityManager) {
        return PageFormConverter.INSTANCE.updater(form, entity, entityManager, identityProvider);
    }
}
