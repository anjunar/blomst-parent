package com.anjunar.blomst.control.roles;

import com.anjunar.blomst.control.roles.role.RoleForm;
import com.anjunar.blomst.control.roles.role.RoleResource;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
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

    private final ResourceEntityMapper mapper;


    @Inject
    public RolesResource(RolesService rolesService, ResourceEntityMapper mapper) {
        this.rolesService = rolesService;
        this.mapper = mapper;
    }

    public RolesResource() {
        this(null, null);
    }

    @Override
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Roles Table")
    public Table<RoleForm> list(RolesSearch search) {

        List<Role> roles = rolesService.find(search);
        long count = rolesService.count(search);

        List<RoleForm> resources = new ArrayList<>();
        Table<RoleForm> table = new Table<>(resources, count) {};
        for (Role role : roles) {
            RoleForm resource = mapper.map(role, RoleForm.class, table);

            resources.add(resource);

            linkTo(methodOn(RoleResource.class).read(role.getId()))
                    .build(resource::addLink);
        }

        return table;
    }
}


