package com.anjunar.blomst.social.communities.community.connections.connection;

import com.anjunar.blomst.control.roles.role.RoleForm;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.AbstractRestEntityConverter;
import com.anjunar.common.rest.api.json.annotations.JsonSchema;
import com.anjunar.common.rest.api.json.schema.JsonNode;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.Role;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.communities.CommunitiesConnection;
import com.anjunar.blomst.social.communities.Community;
import com.anjunar.blomst.social.communities.Status;
import com.anjunar.blomst.social.communities.community.CommunityForm;

import javax.persistence.EntityManager;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class CommunityConnectionForm extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "From", readOnly = true)
    private UserSelect from;

    @JsonSchema(widget = JsonNode.Widget.SELECT, title = "Status")
    private Status status;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Role")
    private RoleForm role;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "To", readOnly = true)
    private CommunityForm to;

    public UserSelect getFrom() {
        return from;
    }

    public void setFrom(UserSelect from) {
        this.from = from;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public RoleForm getRole() {
        return role;
    }

    public void setRole(RoleForm role) {
        this.role = role;
    }

    public CommunityForm getTo() {
        return to;
    }

    public void setTo(CommunityForm to) {
        this.to = to;
    }

    public static class ConnectionFormConverter extends AbstractRestEntityConverter<CommunitiesConnection, CommunityConnectionForm> {

        public static final ConnectionFormConverter INSTANCE = new ConnectionFormConverter();

        @Override
        public CommunitiesConnection updater(CommunityConnectionForm restEntity, CommunitiesConnection entity, EntityManager entityManager, IdentityProvider identityProvider) {
            entity.setFrom(identityProvider.getUser());
            entity.setRole(entityManager.find(Role.class, restEntity.getRole().getId()));
            entity.setStatus(restEntity.getStatus());
            entity.setTo(entityManager.find(Community.class, restEntity.getTo().getId()));
            return entity;
        }

        @Override
        public CommunityConnectionForm factory(CommunityConnectionForm restEntity, CommunitiesConnection entity) {
            CommunityConnectionForm form = super.factory(restEntity, entity);
            form.setFrom(UserSelect.factory(entity.getFrom()));
            form.setRole(RoleForm.factory(entity.getRole()));
            form.setStatus(entity.getStatus());
            form.setTo(CommunityForm.factory(entity.getTo()));
            return form;
        }
    }

    public static CommunityConnectionForm factory(CommunitiesConnection entity) {
        return CommunityConnectionForm.ConnectionFormConverter.INSTANCE.factory(new CommunityConnectionForm(), entity);
    }

    public static CommunitiesConnection updater(CommunityConnectionForm resource, CommunitiesConnection entity, IdentityProvider identityProvider, EntityManager entityManager) {
        return CommunityConnectionForm.ConnectionFormConverter.INSTANCE.updater(resource, entity, entityManager, identityProvider);
    }

}
