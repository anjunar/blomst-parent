package com.anjunar.blomst.control.roles.role;

import com.anjunar.blomst.social.sites.SiteConnection;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.objectmapper.NewInstanceProvider;
import com.anjunar.common.rest.objectmapper.ObjectMapper;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.Role;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import java.util.UUID;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.*;

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

        ObjectMapper mapper = new ObjectMapper();
        RoleForm resource = mapper.map(role, RoleForm.class);

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
    public RoleForm save(RoleForm form) {

        NewInstanceProvider instanceProvider = (uuid, sourceClass) -> entityManager.find(sourceClass, uuid);
        ObjectMapper mapper = new ObjectMapper(instanceProvider);
        Role role = mapper.map(form, Role.class);

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
    public RoleForm update(UUID id, RoleForm form) {

        NewInstanceProvider instanceProvider = (uuid, sourceClass) -> entityManager.find(sourceClass, uuid);
        ObjectMapper mapper = new ObjectMapper(instanceProvider);
        Role role = mapper.map(form, Role.class);

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
