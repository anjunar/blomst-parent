package com.anjunar.blomst.social.communities.community.connections.connection;

import com.anjunar.blomst.control.roles.RolesResource;
import com.anjunar.blomst.control.roles.RolesSearch;
import com.anjunar.blomst.control.roles.role.RoleForm;
import com.anjunar.common.rest.LinkDescription;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.api.json.schema.JsonObject;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.communities.CommunitiesConnection;
import com.anjunar.blomst.social.communities.Community;
import com.anjunar.blomst.social.communities.Status;
import com.anjunar.blomst.social.communities.community.CommunityForm;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.UUID;

import static com.anjunar.common.rest.WebURLBuilderFactory.*;

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

        form.setTo(CommunityForm.factory(entityManager.find(Community.class, to)));
        form.setStatus(Status.PENDING);
        form.setRole(RoleForm.factory(service.findUserRole()));
        form.setFrom(UserSelect.factory(identityProvider.getUser()));

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
        CommunityConnectionForm form = CommunityConnectionForm.factory(entity);

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
        CommunitiesConnection entity = new CommunitiesConnection();
        CommunityConnectionForm.updater(form, entity, identityProvider, entityManager);

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

        CommunitiesConnection entity = entityManager.find(CommunitiesConnection.class, id);

        CommunityConnectionForm.updater(form, entity, identityProvider, entityManager);

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
