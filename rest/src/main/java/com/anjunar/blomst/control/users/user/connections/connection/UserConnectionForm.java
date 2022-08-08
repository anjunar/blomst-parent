package com.anjunar.blomst.control.users.user.connections.connection;

import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.AbstractRestEntityConverter;
import com.anjunar.common.rest.api.json.annotations.JsonSchema;
import com.anjunar.common.rest.api.json.schema.JsonNode;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.User;
import com.anjunar.blomst.control.users.UserConnection;
import com.anjunar.blomst.control.users.user.connections.categories.category.CategoryForm;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.control.users.Category;

import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotNull;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class UserConnectionForm extends AbstractRestEntity {

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "From", readOnly = true)
    private UserSelect from;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Category")
    private CategoryForm category;

    @JsonSchema(widget = JsonNode.Widget.CHECKBOX, title = "Accepted", readOnly = true)
    private boolean accepted;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "To", readOnly = true)
    private UserSelect to;

    public UserSelect getFrom() {
        return from;
    }

    public void setFrom(UserSelect from) {
        this.from = from;
    }

    public CategoryForm getCategory() {
        return category;
    }

    public void setCategory(CategoryForm category) {
        this.category = category;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public UserSelect getTo() {
        return to;
    }

    public void setTo(UserSelect to) {
        this.to = to;
    }

    public static class ConnectionFormConverter extends AbstractRestEntityConverter<UserConnection, UserConnectionForm> {

        public static final ConnectionFormConverter INSTANCE = new ConnectionFormConverter();

        @Override
        public UserConnectionForm factory(UserConnectionForm restEntity, UserConnection entity) {
            UserConnectionForm connectionForm = super.factory(restEntity, entity);

            connectionForm.setFrom(UserSelect.factory(entity.getFrom()));
            connectionForm.setTo(UserSelect.factory(entity.getTo()));
            connectionForm.setCategory(CategoryForm.factory(entity.getCategory()));

            return connectionForm;
        }

        @Override
        public UserConnection updater(UserConnectionForm restEntity, UserConnection entity, EntityManager entityManager, IdentityProvider identityProvider) {

            entity.setFrom(identityProvider.getUser());
            entity.setTo(entityManager.find(User.class, restEntity.getTo().getId()));
            entity.setCategory(entityManager.find(Category.class, restEntity.getCategory().getId()));

            return entity;
        }
    }

    public static UserConnectionForm factory(UserConnection user) {
        return UserConnectionForm.ConnectionFormConverter.INSTANCE.factory(new UserConnectionForm(), user);
    }

    public static UserConnection updater(UserConnectionForm resource, UserConnection entity, EntityManager entityManager, IdentityProvider identityProvider) {
        return UserConnectionForm.ConnectionFormConverter.INSTANCE.updater(resource, entity, entityManager, identityProvider);
    }

}
