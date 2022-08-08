package com.anjunar.blomst.control.roles.role;

import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.AbstractRestEntityConverter;
import com.anjunar.common.rest.api.json.annotations.JsonSchema;
import com.anjunar.common.rest.api.json.schema.JsonNode;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.Role;

import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class RoleForm extends AbstractRestEntity {

    @NotBlank
    @Size(min = 3, max = 80)
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Name", naming = true)
    private String name;

    @Size(max = 255)
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Description")
    private String description;

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

    public static class RoleFormConverter extends AbstractRestEntityConverter<Role, RoleForm> {

        public static RoleFormConverter INSTANCE = new RoleFormConverter();

        @Override
        public RoleForm factory(RoleForm resource, Role role) {
            resource.setId(role.getId());
            resource.setName(role.getName());
            resource.setDescription(role.getDescription());
            return super.factory(resource, role);
        }

        @Override
        public Role updater(RoleForm resource, Role role, EntityManager entityManager, IdentityProvider identityProvider) {
            role.setName(resource.getName());
            role.setDescription(resource.getDescription());
            return role;
        }
    }

    public static RoleForm factory(Role role) {
        return RoleFormConverter.INSTANCE.factory(new RoleForm(), role);
    }

    public static Role updater(RoleForm resource, Role role, EntityManager entityManager, IdentityProvider identityProvider) {
        return RoleFormConverter.INSTANCE.updater(resource, role, entityManager, identityProvider);
    }


}
