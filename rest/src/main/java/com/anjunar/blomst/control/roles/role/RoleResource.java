package com.anjunar.blomst.control.roles.role;

import com.anjunar.common.rest.LinkDescription;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.Role;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.UUID;

import static com.anjunar.common.rest.WebURLBuilderFactory.*;

@ApplicationScoped
@Path("control/roles/role")
public class RoleResource implements FormResourceTemplate<RoleForm> {

    private final EntityManager entityManager;

    private final IdentityProvider identityProvider;

    @Inject
    public RoleResource(EntityManager entityManager, IdentityProvider identityProvider) {
        this.entityManager = entityManager;
        this.identityProvider = identityProvider;
    }

    public RoleResource() {
        this(null, null);
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

        RoleForm resource = RoleForm.factory(role);

        linkTo(methodOn(RoleResource.class).update(role.getId(), new RoleForm()))
                .build(resource::addLink);
        linkTo(methodOn(RoleResource.class).delete(role.getId()))
                .build(resource::addLink);


        return resource;
    }

    @Override
    @Transactional
    @RolesAllowed("Administrator")
    @LinkDescription("Save Role")
    public RoleForm save(@Valid RoleForm form) {

        Role role = new Role();

        RoleForm.updater(form, role, entityManager, identityProvider);

        entityManager.persist(role);
        form.setId(role.getId());

        linkTo(methodOn(RoleResource.class).update(role.getId(), new RoleForm()))
                .build(form::addLink);
        linkTo(methodOn(RoleResource.class).delete(role.getId()))
                .build(form::addLink);

        return form;
    }

    @Override
    @Transactional
    @RolesAllowed("Administrator")
    @LinkDescription("Update Role")
    public RoleForm update(UUID id, @Valid RoleForm form) {

        Role role = entityManager.find(Role.class, id);

        RoleForm.updater(form, role, entityManager, identityProvider);

        linkTo(methodOn(RoleResource.class).update(role.getId(), new RoleForm()))
                .build(form::addLink);
        linkTo(methodOn(RoleResource.class).delete(role.getId()))
                .build(form::addLink);

        return form;
    }

    @Override
    @Transactional
    @RolesAllowed("Administrator")
    @LinkDescription("Delete Role")
    public ResponseOk delete(UUID id) {
        Role role = entityManager.find(Role.class, id);
        entityManager.remove(role);
        return new ResponseOk();
    }
}
