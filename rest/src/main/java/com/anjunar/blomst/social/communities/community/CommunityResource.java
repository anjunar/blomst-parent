package com.anjunar.blomst.social.communities.community;

import com.anjunar.blomst.social.communities.community.connections.CommunityConnectionsResource;
import com.anjunar.blomst.social.communities.community.connections.CommunityConnectionsSearch;
import com.anjunar.blomst.social.communities.community.connections.connection.CommunityConnectionResource;
import com.google.common.collect.Sets;
import com.anjunar.common.rest.LinkDescription;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.social.communities.CommunitiesConnection;
import com.anjunar.blomst.social.communities.Community;
import com.anjunar.blomst.social.timeline.TimelineResource;
import com.anjunar.blomst.social.timeline.TimelineSearch;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static com.anjunar.common.rest.WebURLBuilderFactory.*;

@ApplicationScoped
@Path("social/communities/community")
public class CommunityResource implements FormResourceTemplate<CommunityForm> {

    private final EntityManager entityManager;

    private final IdentityProvider identityProvider;

    private final CommunityService service;

    @Inject
    public CommunityResource(EntityManager entityManager, IdentityProvider identity, CommunityService service) {
        this.entityManager = entityManager;
        this.identityProvider = identity;
        this.service = service;
    }

    public CommunityResource() {
        this(null, null, null);
    }

    @Produces("application/json")
    @GET
    @Path("create")
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Create Community")
    public CommunityForm create() {
        CommunityForm form = new CommunityForm();

        linkTo(methodOn(CommunityResource.class).save(new CommunityForm()))
                .build(form::addLink);

        return form;
    }

    @Transactional
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Read Community")
    @Override
    public CommunityForm read(UUID id) {
        Community community = entityManager.find(Community.class, id);
        CommunityForm form = CommunityForm.factory(community);

        try {
            CommunitiesConnection connection = service.findConnection(identityProvider.getUser().getId(), id);
            linkTo(methodOn(CommunityConnectionResource.class).read(connection.getId()))
                    .withRel("connection")
                    .build(form::addLink);
        } catch (NoResultException e) {
            linkTo(methodOn(CommunityConnectionResource.class).create(id))
                    .withRel("connect")
                    .build(form::addLink);
        }

        linkTo(methodOn(CommunityResource.class).update(id, new CommunityForm()))
                .build(form::addLink);

        linkTo(methodOn(CommunityResource.class).delete(id))
                .build(form::addLink);

        TimelineSearch timelineSearch = new TimelineSearch();
        timelineSearch.setSource(Sets.newHashSet(id));
        linkTo(methodOn(TimelineResource.class).list(timelineSearch))
                .withRel("timeline")
                .build(form::addLink);

        CommunityConnectionsSearch connectionsSearch = new CommunityConnectionsSearch();
        connectionsSearch.setTo(id);
        linkTo(methodOn(CommunityConnectionsResource.class).list(connectionsSearch))
                .withRel("connections")
                .build(form::addLink);

        return form;
    }

    @Transactional
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Save Community")
    @Override
    public CommunityForm save(CommunityForm form) {

        Community entity = new Community();

        CommunityForm.updater(form, entity, identityProvider, entityManager);

        entityManager.persist(entity);
        form.setId(entity.getId());

        service.addAdministrator(entity);

        linkTo(methodOn(CommunityResource.class).update(entity.getId(), new CommunityForm()))
                .build(form::addLink);

        linkTo(methodOn(CommunityResource.class).delete(entity.getId()))
                .build(form::addLink);

        return form;
    }

    @Transactional
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Update Community")
    @Override
    public CommunityForm update(UUID id, CommunityForm form) {

        if (service.hasRole("Administrator", id)) {
            Community entity = entityManager.find(Community.class, id);

            CommunityForm.updater(form, entity, identityProvider, entityManager);

            linkTo(methodOn(CommunityResource.class).update(id, new CommunityForm()))
                    .build(form::addLink);

            linkTo(methodOn(CommunityResource.class).delete(id))
                    .build(form::addLink);

            return form;
        }

        throw new WebApplicationException(Response.Status.FORBIDDEN);
    }

    @Transactional
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Delete Community")
    @Override
    public ResponseOk delete(UUID id) {
        if (service.hasRole("Administrator", id)) {
            Community entity = entityManager.find(Community.class, id);
            entity.setDeleted(true);
            return new ResponseOk();
        }
        throw new WebApplicationException(Response.Status.FORBIDDEN);
    }

}
