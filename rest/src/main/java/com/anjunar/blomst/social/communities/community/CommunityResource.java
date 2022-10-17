package com.anjunar.blomst.social.communities.community;

import com.anjunar.blomst.control.roles.RolesResource;
import com.anjunar.blomst.control.roles.RolesSearch;
import com.anjunar.blomst.social.communities.CommunitiesResource;
import com.anjunar.blomst.social.communities.CommunitiesSearch;
import com.anjunar.blomst.social.communities.community.connections.CommunityConnectionsResource;
import com.anjunar.blomst.social.communities.community.connections.CommunityConnectionsSearch;
import com.anjunar.blomst.social.communities.community.connections.connection.CommunityConnectionResource;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.rest.mapper.ResourceRestMapper;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.google.common.collect.Sets;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.blomst.social.communities.CommunitiesConnection;
import com.anjunar.blomst.social.communities.Community;
import com.anjunar.blomst.social.timeline.TimelineResource;
import com.anjunar.blomst.social.timeline.TimelineSearch;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import java.util.UUID;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.*;

@ApplicationScoped
@Path("social/communities/community")
public class CommunityResource implements FormResourceTemplate<CommunityForm> {

    private final EntityManager entityManager;

    private final IdentityManager identityManager;

    private final CommunityService service;

    private final ResourceEntityMapper entityMapper;

    private final ResourceRestMapper restMapper;


    @Inject
    public CommunityResource(EntityManager entityManager, IdentityManager identity, CommunityService service, ResourceEntityMapper entityMapper, ResourceRestMapper restMapper) {
        this.entityManager = entityManager;
        this.identityManager = identity;
        this.service = service;
        this.entityMapper = entityMapper;
        this.restMapper = restMapper;
    }

    public CommunityResource() {
        this(null, null, null, null, null);
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

    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Read Community")
    @Override
    public CommunityForm read(UUID id) {
        Community community = entityManager.find(Community.class, id);

        CommunityForm form = entityMapper.map(community, CommunityForm.class);

        try {
            CommunitiesConnection connection = service.findConnection(identityManager.getUser().getId(), id);
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

    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Save Community")
    @Override
    public ResponseOk save(CommunityForm form) {

        Community entity = restMapper.map(form, Community.class);

        entityManager.persist(entity);
        form.setId(entity.getId());

        service.addAdministrator(entity);

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(CommunitiesResource.class).list(new CommunitiesSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Update Community")
    @Override
    public ResponseOk update(UUID id, CommunityForm form) {

        if (service.hasRole("Administrator", id)) {
            restMapper.map(form, Community.class);

            ResponseOk response = new ResponseOk();

            linkTo(methodOn(CommunitiesResource.class).list(new CommunitiesSearch()))
                    .withRel("redirect")
                    .build(response::addLink);

            return response;
        }

        throw new WebApplicationException(Response.Status.FORBIDDEN);
    }

    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Delete Community")
    @Override
    public ResponseOk delete(UUID id) {
        if (service.hasRole("Administrator", id)) {
            Community entity = entityManager.find(Community.class, id);
            entity.setDeleted(true);
            ResponseOk response = new ResponseOk();

            linkTo(methodOn(CommunitiesResource.class).list(new CommunitiesSearch()))
                    .withRel("redirect")
                    .build(response::addLink);

            return response;
        }
        throw new WebApplicationException(Response.Status.FORBIDDEN);
    }

}
