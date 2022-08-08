package com.anjunar.blomst.control.users.user.connections;

import com.anjunar.blomst.control.users.user.connections.categories.category.CategoryForm;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.AbstractRestEntityConverter;
import com.anjunar.common.rest.api.json.annotations.JsonSchema;
import com.anjunar.common.rest.api.json.schema.JsonNode;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.User;
import com.anjunar.blomst.control.users.Category;
import com.anjunar.blomst.control.users.UserConnection;
import com.anjunar.blomst.shared.users.user.UserSelect;

import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotNull;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class ConnectionRow extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Category")
    private CategoryForm category;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "To", readOnly = true)
    private UserSelect to;

    @JsonSchema(widget = JsonNode.Widget.CHECKBOX, title = "Accepted", readOnly = true)
    private boolean accepted;

    public CategoryForm getCategory() {
        return category;
    }

    public void setCategory(CategoryForm category) {
        this.category = category;
    }

    public UserSelect getTo() {
        return to;
    }

    public void setTo(UserSelect to) {
        this.to = to;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public static class ConnectionFormConverter extends AbstractRestEntityConverter<UserConnection, ConnectionRow> {

        public static final ConnectionFormConverter INSTANCE = new ConnectionFormConverter();

        @Override
        public ConnectionRow factory(ConnectionRow restEntity, UserConnection entity) {
            ConnectionRow connectionForm = super.factory(restEntity, entity);
            connectionForm.setTo(UserSelect.factory(entity.getTo()));
            connectionForm.setCategory(CategoryForm.factory(entity.getCategory()));
            return connectionForm;
        }

        @Override
        public UserConnection updater(ConnectionRow restEntity, UserConnection entity, EntityManager entityManager, IdentityProvider identityProvider) {

            entity.setFrom(identityProvider.getUser());
            entity.setTo(entityManager.find(User.class, restEntity.getTo().getId()));
            entity.setCategory(entityManager.find(Category.class, restEntity.getCategory().getId()));

            return entity;
        }
    }

    public static ConnectionRow factory(UserConnection user) {
        return ConnectionRow.ConnectionFormConverter.INSTANCE.factory(new ConnectionRow(), user);
    }

    public static UserConnection updater(ConnectionRow resource, UserConnection entity, EntityManager entityManager, IdentityProvider identityProvider) {
        return ConnectionRow.ConnectionFormConverter.INSTANCE.updater(resource, entity, entityManager, identityProvider);
    }

}
