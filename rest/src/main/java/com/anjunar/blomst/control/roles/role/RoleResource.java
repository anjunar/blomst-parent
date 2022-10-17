package com.anjunar.blomst.control.roles.role;

import com.anjunar.blomst.control.roles.RolesResource;
import com.anjunar.blomst.control.roles.RolesSearch;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.rest.mapper.ResourceRestMapper;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.Role;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import java.util.UUID;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.*;

@ApplicationScoped
@Path("control/roles/role")
public class RoleResource implements FormResourceTemplate<RoleForm> {

    private final EntityManager entityManager;

    private final IdentityManager identityManager;

    private final ResourceEntityMapper entityMapper;

    private final ResourceRestMapper restMapper;


    @Inject
    public RoleResource(EntityManager entityManager, IdentityManager identityManager, ResourceEntityMapper entityMapper, ResourceRestMapper restMapper) {
        this.entityManager = entityManager;
        this.identityManager = identityManager;
        this.entityMapper = entityMapper;
        this.restMapper = restMapper;
    }

    public RoleResource() {
        this(null, null, null, null);
    }

    @Produces("application/json")
    @GET
    @Path("create")
    @RolesAllowed("Administrator")
    @LinkDescription("Create Role")
    public RoleForm create() {
        RoleForm resource = new RoleForm();

        linkTo(methodOn(RoleResource.class).save(new RoleForm()))
                        .build(resource::addLink);

        return resource;
    }

    @Override
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Read Role")
    public RoleForm read(UUID id) {

        Role role = entityManager.find(Role.class, id);

        RoleForm resource = entityMapper.map(role, RoleForm.class);

        linkTo(methodOn(RoleResource.class).update(role.getId(), new RoleForm()))
                .build(resource::addLink);
        linkTo(methodOn(RoleResource.class).delete(role.getId()))
                .build(resource::addLink);


        return resource;
    }

    @Override
    @RolesAllowed("Administrator")
    @LinkDescription("Save Role")
    public ResponseOk save(RoleForm form) {

        Role role = restMapper.map(form, Role.class);

        entityManager.persist(role);
        form.setId(role.getId());

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(RolesResource.class).list(new RolesSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    @Override
    @RolesAllowed("Administrator")
    @LinkDescription("Update Role")
    public ResponseOk update(UUID id, RoleForm form) {

        restMapper.map(form, Role.class);

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(RolesResource.class).list(new RolesSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    @Override
    @RolesAllowed("Administrator")
    @LinkDescription("Delete Role")
    public ResponseOk delete(UUID id) {
        Role role = entityManager.find(Role.class, id);
        entityManager.remove(role);
        ResponseOk response = new ResponseOk();

        linkTo(methodOn(RolesResource.class).list(new RolesSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }
}
