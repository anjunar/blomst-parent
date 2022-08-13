package com.anjunar.blomst.control.roles;

import com.anjunar.blomst.control.roles.role.RoleForm;
import com.anjunar.blomst.control.roles.role.RoleResource;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.security.Role;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.linkTo;
import static com.anjunar.common.rest.link.WebURLBuilderFactory.methodOn;

@ApplicationScoped
@Path("control/roles")
public class RolesResource implements ListResourceTemplate<RoleForm, RolesSearch> {

    private final RolesService rolesService;

    @Inject
    public RolesResource(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    public RolesResource() {
        this(null);
    }

    @Override
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Roles Table")
    public Table<RoleForm> list(RolesSearch search) {

        List<Role> roles = rolesService.find(search);
        long count = rolesService.count(search);

        List<RoleForm> resources = new ArrayList<>();
        for (Role role : roles) {
            RoleForm resource = RoleForm.factory(role);

            resources.add(resource);

            linkTo(methodOn(RoleResource.class).read(role.getId()))
                    .build(resource::addLink);
        }

        return new Table<>(resources, count) {};
    }
}


