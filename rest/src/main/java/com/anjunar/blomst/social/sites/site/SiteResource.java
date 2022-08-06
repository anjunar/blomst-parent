package com.anjunar.blomst.social.sites.site;

import com.anjunar.blomst.social.sites.site.connections.SiteConnectionsResource;
import com.anjunar.blomst.social.sites.site.connections.SiteConnectionsSearch;
import com.anjunar.blomst.social.sites.site.connections.connection.SiteConnectionResource;
import com.anjunar.blomst.social.timeline.TimelineResource;
import com.anjunar.blomst.social.timeline.TimelineSearch;
import com.google.common.collect.Sets;
import com.anjunar.common.rest.LinkDescription;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.social.sites.Site;
import com.anjunar.blomst.social.sites.SiteConnection;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.UUID;

import static com.anjunar.common.rest.WebURLBuilderFactory.*;

@Path("social/sites/site")
@ApplicationScoped
public class SiteResource implements FormResourceTemplate<SiteForm> {

    private final EntityManager entityManager;

    private final IdentityProvider identityProvider;

    private final SiteService service;

    @Inject
    public SiteResource(EntityManager entityManager, IdentityProvider identityProvider, SiteService service) {
        this.entityManager = entityManager;
        this.identityProvider = identityProvider;
        this.service = service;
    }

    public SiteResource() {
        this(null, null, null);
    }

    @Produces("application/json")
    @GET
    @Path("create")
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Create Site")
    public SiteForm create() {
        final SiteForm form = new SiteForm();

        linkTo(methodOn(SiteResource.class).save(new SiteForm()))
                .build(form::addLink);

        return form;
    }

    @Transactional
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Read Site")
    @Override
    public SiteForm read(UUID id) {
        final Site entity = entityManager.find(Site.class, id);
        final SiteForm form = SiteForm.factory(entity);

        try {
            SiteConnection connection = service.findConnection(identityProvider.getUser().getId(), id);
            linkTo(methodOn(SiteConnectionResource.class).read(connection.getId()))
                    .withRel("connection")
                    .build(form::addLink);
        } catch (NoResultException e) {
            linkTo(methodOn(SiteConnectionResource.class).create(id))
                    .withRel("connect")
                    .build(form::addLink);
        }

        TimelineSearch timelineSearch = new TimelineSearch();
        timelineSearch.setSource(Sets.newHashSet(id));
        linkTo(methodOn(TimelineResource.class).list(timelineSearch))
                .withRel("timeline")
                .build(form::addLink);

        SiteConnectionsSearch siteConnectionsSearch = new SiteConnectionsSearch();
        siteConnectionsSearch.setTo(id);
        linkTo(methodOn(SiteConnectionsResource.class).list(siteConnectionsSearch))
                .withRel("connections")
                .build(form::addLink);

        linkTo(methodOn(SiteResource.class).update(id, new SiteForm()))
                .build(form::addLink);
        linkTo(methodOn(SiteResource.class).delete(id))
                .build(form::addLink);

        return form;
    }

    @Transactional
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Save Site")
    @Override
    public SiteForm save(SiteForm form) {
        final Site entity = new Site();

        SiteForm.updater(form, entity, identityProvider, entityManager);

        entityManager.persist(entity);

        linkTo(methodOn(SiteResource.class).update(entity.getId(), new SiteForm()))
                .build(form::addLink);

        linkTo(methodOn(SiteResource.class).delete(entity.getId()))
                .build(form::addLink);

        return form;
    }

    @Transactional
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Update Site")
    @Override
    public SiteForm update(UUID id, SiteForm form) {
        final Site entity = entityManager.find(Site.class, id);

        SiteForm.updater(form, entity, identityProvider, entityManager);

        linkTo(methodOn(SiteResource.class).update(id, new SiteForm()))
                .build(form::addLink);

        linkTo(methodOn(SiteResource.class).delete(id))
                .build(form::addLink);

        return form;
    }

    @Transactional
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Delete Site")
    @Override
    public ResponseOk delete(UUID id) {
        Site entity = entityManager.getReference(Site.class, id);

        entityManager.remove(entity);

        return new ResponseOk();
    }

}
