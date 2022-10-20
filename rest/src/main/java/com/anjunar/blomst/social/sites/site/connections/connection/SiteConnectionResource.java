package com.anjunar.blomst.social.sites.site.connections.connection;

import com.anjunar.blomst.control.roles.RolesResource;
import com.anjunar.blomst.control.roles.RolesSearch;
import com.anjunar.blomst.control.users.user.UserForm;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.sites.SitesResource;
import com.anjunar.blomst.social.sites.SitesSearch;
import com.anjunar.blomst.social.sites.site.connections.SiteConnectionsResource;
import com.anjunar.blomst.social.sites.site.connections.SiteConnectionsSearch;
import com.anjunar.common.rest.api.AbstractSchemaEntity;
import com.anjunar.common.rest.api.Form;
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
public class SiteConnectionResource implements FormResourceTemplate<Form<SiteConnectionForm>> {

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
    public Form<SiteConnectionForm> create() {
        SiteConnectionForm resource = new SiteConnectionForm();
        Form<SiteConnectionForm> form = new Form<>(resource) {};

        resource.setFrom(entityMapper.map(identityManager.getUser(), UserSelect.class, form, "from"));

        linkTo(methodOn(SiteConnectionResource.class).save(new Form<>()))
                .build(form::addLink);

        JsonObject to = form.find("to", JsonObject.class);
        linkTo(methodOn(SitesResource.class).list(new SitesSearch()))
                .build(to::addLink);

        return form;
    }

    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Read Site Connection")
    @Override
    public Form<SiteConnectionForm> read(UUID id) {

        SiteConnection entity = entityManager.find(SiteConnection.class, id);
        Form<SiteConnectionForm> form = entityMapper.map(entity, new Form<>() {});

        linkTo(methodOn(SiteConnectionResource.class).update(id, new Form<>()))
                .build(form::addLink);

        linkTo(methodOn(SiteConnectionResource.class).delete(id))
                .build(form::addLink);

        return form;
    }

    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Save Site Connection")
    @Override
    public ResponseOk save(Form<SiteConnectionForm> form) {

        SiteConnection entity = restMapper.map(form, SiteConnection.class);

        entityManager.persist(entity);

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(SiteConnectionsResource.class).list(new SiteConnectionsSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Update Site Connection")
    @Override
    public ResponseOk update(UUID id, Form<SiteConnectionForm> form) {
        restMapper.map(form, SiteConnection.class);

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(SiteConnectionsResource.class).list(new SiteConnectionsSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Delete Site Connection")
    @Override
    public ResponseOk delete(UUID id) {
        SiteConnection entity = entityManager.getReference(SiteConnection.class, id);
        entityManager.remove(entity);
        ResponseOk response = new ResponseOk();

        linkTo(methodOn(SiteConnectionsResource.class).list(new SiteConnectionsSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }
}
