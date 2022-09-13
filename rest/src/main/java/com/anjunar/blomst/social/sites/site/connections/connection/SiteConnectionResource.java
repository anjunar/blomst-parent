package com.anjunar.blomst.social.sites.site.connections.connection;

import com.anjunar.blomst.control.users.user.UserForm;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.sites.SitesResource;
import com.anjunar.blomst.social.sites.SitesSearch;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.rest.mapper.ResourceRestMapper;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.blomst.social.sites.SiteConnection;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import java.util.UUID;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.linkTo;
import static com.anjunar.common.rest.link.WebURLBuilderFactory.methodOn;

@Path("social/sites/site/connections/connection")
@ApplicationScoped
public class SiteConnectionResource implements FormResourceTemplate<SiteConnectionForm> {

    private final EntityManager entityManager;

    private final IdentityManager identityManager;

    private final ResourceEntityMapper entityMapper;

    private final ResourceRestMapper restMapper;


    @Inject
    public SiteConnectionResource(EntityManager entityManager, IdentityManager identityManager, ResourceEntityMapper entityMapper, ResourceRestMapper restMapper) {
        this.entityManager = entityManager;
        this.identityManager = identityManager;
        this.entityMapper = entityMapper;
        this.restMapper = restMapper;
    }

    public SiteConnectionResource() {
        this(null, null, null, null);
    }

    @Produces("application/json")
    @GET
    @Path("create")
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Create Site Connection")
    public SiteConnectionForm create() {
        SiteConnectionForm form = new SiteConnectionForm();

        form.setFrom(entityMapper.map(identityManager.getUser(), UserSelect.class));

        linkTo(methodOn(SiteConnectionResource.class).save(new SiteConnectionForm()))
                .build(form::addLink);

        JsonObject to = form.find("to", JsonObject.class);
        linkTo(methodOn(SitesResource.class).list(new SitesSearch()))
                .build(to::addLink);

        return form;
    }

    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Read Site Connection")
    @Override
    public SiteConnectionForm read(UUID id) {

        SiteConnection entity = entityManager.find(SiteConnection.class, id);
        SiteConnectionForm form = entityMapper.map(entity, SiteConnectionForm.class);

        linkTo(methodOn(SiteConnectionResource.class).update(id, new SiteConnectionForm()))
                .build(form::addLink);

        linkTo(methodOn(SiteConnectionResource.class).delete(id))
                .build(form::addLink);

        return form;
    }

    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Save Site Connection")
    @Override
    public SiteConnectionForm save(SiteConnectionForm form) {

        SiteConnection entity = restMapper.map(form, SiteConnection.class);

        entityManager.persist(entity);

        linkTo(methodOn(SiteConnectionResource.class).update(entity.getId(), new SiteConnectionForm()))
                .build(form::addLink);
        linkTo(methodOn(SiteConnectionResource.class).delete(entity.getId()))
                .build(form::addLink);

        return form;
    }

    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Update Site Connection")
    @Override
    public SiteConnectionForm update(UUID id, SiteConnectionForm form) {
        restMapper.map(form, SiteConnection.class);

        linkTo(methodOn(SiteConnectionResource.class).update(id, new SiteConnectionForm()))
                .build(form::addLink);

        linkTo(methodOn(SiteConnectionResource.class).delete(id))
                .build(form::addLink);

        return form;
    }

    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Delete Site Connection")
    @Override
    public ResponseOk delete(UUID id) {
        SiteConnection entity = entityManager.getReference(SiteConnection.class, id);
        entityManager.remove(entity);
        return new ResponseOk();
    }
}
