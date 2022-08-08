package com.anjunar.blomst.control.users.user.connections.categories.category;

import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.AbstractRestEntityConverter;
import com.anjunar.common.rest.api.json.annotations.JsonSchema;
import com.anjunar.common.rest.api.json.schema.JsonNode;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.control.users.Category;

import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class CategoryForm extends AbstractRestEntity {

    @NotBlank
    @Size(min = 3, max = 80)
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Name", naming = true)
    private String name;

    @Size(max = 255)
    @JsonSchema(widget = JsonNode.Widget.TEXTAREA, title = "Description")
    private String description;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Owner")
    @NotNull
    private UserSelect owner;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserSelect getOwner() {
        return owner;
    }

    public void setOwner(UserSelect owner) {
        this.owner = owner;
    }

    public static class CategoryFormConverter extends AbstractRestEntityConverter<Category, CategoryForm> {

        public static final CategoryFormConverter INSTANCE = new CategoryFormConverter();

        @Override
        public CategoryForm factory(CategoryForm restEntity, Category entity) {
            CategoryForm categoryForm = super.factory(restEntity, entity);
            categoryForm.setName(entity.getName());
            categoryForm.setDescription(entity.getDescription());
            categoryForm.setOwner(UserSelect.factory(entity.getOwner()));
            return categoryForm;
        }

        @Override
        public Category updater(CategoryForm restEntity, Category entity, EntityManager entityManager, IdentityProvider identityProvider) {
            entity.setOwner(identityProvider.getUser());
            entity.setName(restEntity.getName());
            entity.setDescription(restEntity.getDescription());
            return entity;
        }
    }

    public static CategoryForm factory(Category category) {
        return CategoryForm.CategoryFormConverter.INSTANCE.factory(new CategoryForm(), category);
    }

    public static Category updater(CategoryForm resource, Category entity, EntityManager entityManager, IdentityProvider identityProvider) {
        return CategoryForm.CategoryFormConverter.INSTANCE.updater(resource, entity, entityManager, identityProvider);
    }

}
