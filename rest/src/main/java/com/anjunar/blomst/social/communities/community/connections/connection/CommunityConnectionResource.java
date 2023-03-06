package com.anjunar.blomst.social.communities.community.connections.connection;

import com.anjunar.blomst.control.roles.RolesResource;
import com.anjunar.blomst.control.roles.RolesSearch;
import com.anjunar.blomst.control.roles.role.RoleForm;
import com.anjunar.blomst.shared.users.UserSelectResource;
import com.anjunar.blomst.shared.users.UserSelectSearch;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.communities.*;
import com.anjunar.blomst.social.communities.community.connections.CommunityConnectionsResource;
import com.anjunar.blomst.social.communities.community.connections.CommunityConnectionsSearch;
import com.anjunar.common.rest.api.Form;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.rest.mapper.ResourceRestMapper;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.blomst.social.communities.community.CommunityForm;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import java.util.UUID;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.*;

@ApplicationScoped
@Path("social/communities/community/connections/connection")
public class CommunityConnectionResource implements FormResourceTemplate<Form<CommunityConnectionForm>> {

    private final EntityManager entityManager;

    private final IdentityManager identityManager;

    private final CommunityConnectionService service;

    private final ResourceEntityMapper entityMapper;

    private final ResourceRestMapper restMapper;


    @Inject
    public CommunityConnectionResource(EntityManager entityManager, IdentityManager identityManager, CommunityConnectionService service, ResourceEntityMapper entityMapper, ResourceRestMapper restMapper) {
        this.entityManager = entityManager;
        this.identityManager = identityManager;
        this.service = service;
        this.entityMapper = entityMapper;
        this.restMapper = restMapper;
    }

    public CommunityConnectionResource() {
        this(null, null, null, null,  null);
    }

    @Produces("application/json")
    @GET
    @Path("create")
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Create Community")
    public Form<CommunityConnectionForm> create(@QueryParam("to") UUID to) {
        CommunityConnectionForm resource = new CommunityConnectionForm();

        Form<CommunityConnectionForm> form = new Form<>(resource) {};
        form.dirty("to", "role");

        resource.setFrom(entityMapper.map(identityManager.getUser(), UserSelect.class));
        resource.setTo(entityMapper.map(entityManager.find(Community.class, to), CommunityForm.class));
        resource.setStatus(Status.PENDING);
        resource.setRole(entityMapper.map(service.findUserRole(), RoleForm.class));

        linkTo(methodOn(CommunityConnectionResource.class).save(new Form<>()))
                .build(form::addLink);

        JsonObject role = form.find("role", JsonObject.class);
        linkTo(methodOn(RolesResource.class).list(new RolesSearch()))
                .build(role::addLink);

        JsonObject from = form.find("from", JsonObject.class);
        linkTo(methodOn(UserSelectResource.class).list(new UserSelectSearch()))
                .build(from::addLink);

        return form;
    }

    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Create Community Connection")
    @Override
    public Form<CommunityConnectionForm> read(UUID id) {
        CommunityConnection entity = entityManager.find(CommunityConnection.class, id);

        Form<CommunityConnectionForm> form = entityMapper.map(entity, new Form<>() {});

        linkTo(methodOn(CommunityConnectionResource.class).update(id, new Form<>()))
                .build(form::addLink);
        linkTo(methodOn(CommunityConnectionResource.class).delete(id))
                .build(form::addLink);

        JsonObject role = form.find("role", JsonObject.class);
        linkTo(methodOn(RolesResource.class).list(new RolesSearch()))
                .build(role::addLink);

        return form;
    }

    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Save Community Connection")
    @Override
    public ResponseOk save(Form<CommunityConnectionForm> form) {

        CommunityConnection entity = restMapper.map(form, CommunityConnection.class);
        entity.setFrom(identityManager.getUser());
        entityManager.persist(entity);

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(CommunityConnectionsResource.class).list(new CommunityConnectionsSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Update Community Connection")
    @Override
    public ResponseOk update(UUID id, Form<CommunityConnectionForm> form) {

        CommunityConnection entity = restMapper.map(form, CommunityConnection.class);
        entity.setFrom(identityManager.getUser());

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(CommunityConnectionsResource.class).list(new CommunityConnectionsSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Delete Community Connection")
    @Override
    public ResponseOk delete(UUID id) {
        CommunityConnection entity = entityManager.getReference(CommunityConnection.class, id);
        entityManager.remove(entity);
        ResponseOk response = new ResponseOk();

        linkTo(methodOn(CommunityConnectionsResource.class).list(new CommunityConnectionsSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }
}
