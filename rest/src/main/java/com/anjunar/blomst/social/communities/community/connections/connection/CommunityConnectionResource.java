package com.anjunar.blomst.social.communities.community.connections.connection;

import com.anjunar.blomst.control.roles.RolesResource;
import com.anjunar.blomst.control.roles.RolesSearch;
import com.anjunar.blomst.control.roles.role.RoleForm;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.schemamapper.NewInstanceProvider;
import com.anjunar.common.rest.schemamapper.ResourceMapper;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.communities.CommunitiesConnection;
import com.anjunar.blomst.social.communities.Community;
import com.anjunar.blomst.social.communities.Status;
import com.anjunar.blomst.social.communities.community.CommunityForm;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import java.util.UUID;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.*;

@ApplicationScoped
@Path("social/communities/community/connections/connection")
public class CommunityConnectionResource implements FormResourceTemplate<CommunityConnectionForm> {

    private final EntityManager entityManager;

    private final IdentityProvider identityProvider;

    private final CommunityConnectionService service;

    @Inject
    public CommunityConnectionResource(EntityManager entityManager, IdentityProvider identityProvider, CommunityConnectionService service) {
        this.entityManager = entityManager;
        this.identityProvider = identityProvider;
        this.service = service;
    }

    public CommunityConnectionResource() {
        this(null, null, null);
    }

    @Produces("application/json")
    @GET
    @Path("create")
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Create Community")
    public CommunityConnectionForm create(@QueryParam("to") UUID to) {
        CommunityConnectionForm form = new CommunityConnectionForm();

        ResourceMapper mapper = new ResourceMapper();

        form.setTo(mapper.map(entityManager.find(Community.class, to), CommunityForm.class));
        form.setStatus(Status.PENDING);
        form.setRole(mapper.map(service.findUserRole(), RoleForm.class));
        form.setFrom(mapper.map(identityProvider.getUser(), UserSelect.class));

        linkTo(methodOn(CommunityConnectionResource.class).save(new CommunityConnectionForm()))
                .build(form::addLink);

        return form;
    }

    @Transactional
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Create Community Connection")
    @Override
    public CommunityConnectionForm read(UUID id) {
        CommunitiesConnection entity = entityManager.find(CommunitiesConnection.class, id);

        ResourceMapper mapper = new ResourceMapper();
        CommunityConnectionForm form = mapper.map(entity, CommunityConnectionForm.class);

        linkTo(methodOn(CommunityConnectionResource.class).update(id, new CommunityConnectionForm()))
                .build(form::addLink);
        linkTo(methodOn(CommunityConnectionResource.class).delete(id))
                .build(form::addLink);

        JsonObject role = form.find("role", JsonObject.class);
        linkTo(methodOn(RolesResource.class).list(new RolesSearch()))
                .build(role::addLink);

        return form;
    }

    @Transactional
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Save Community Connection")
    @Override
    public CommunityConnectionForm save(CommunityConnectionForm form) {

        NewInstanceProvider instanceProvider = (uuid, sourceClass) -> entityManager.find(sourceClass, uuid);
        ResourceMapper mapper = new ResourceMapper(instanceProvider);
        CommunitiesConnection entity = mapper.map(form, CommunitiesConnection.class);

        entityManager.persist(entity);

        linkTo(methodOn(CommunityConnectionResource.class).update(entity.getId(), new CommunityConnectionForm()))
                .build(form::addLink);
        linkTo(methodOn(CommunityConnectionResource.class).delete(entity.getId()))
                .build(form::addLink);


        return form;
    }

    @Transactional
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Update Community Connection")
    @Override
    public CommunityConnectionForm update(UUID id, CommunityConnectionForm form) {

        NewInstanceProvider instanceProvider = (uuid, sourceClass) -> entityManager.find(sourceClass, uuid);
        ResourceMapper mapper = new ResourceMapper(instanceProvider);
        CommunitiesConnection entity = mapper.map(form, CommunitiesConnection.class);

        linkTo(methodOn(CommunityConnectionResource.class).update(entity.getId(), new CommunityConnectionForm()))
                .build(form::addLink);
        linkTo(methodOn(CommunityConnectionResource.class).delete(entity.getId()))
                .build(form::addLink);

        return form;
    }

    @Transactional
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Delete Community Connection")
    @Override
    public ResponseOk delete(UUID id) {
        CommunitiesConnection entity = entityManager.getReference(CommunitiesConnection.class, id);
        entityManager.remove(entity);
        return new ResponseOk();
    }
}
