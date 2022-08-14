package com.anjunar.blomst.social.sites.site.connections.connection;

import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.objectmapper.NewInstanceProvider;
import com.anjunar.common.rest.objectmapper.ObjectMapper;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.sites.Site;
import com.anjunar.blomst.social.sites.SiteConnection;
import com.anjunar.blomst.social.sites.site.SiteForm;

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

import static com.anjunar.common.rest.link.WebURLBuilderFactory.linkTo;
import static com.anjunar.common.rest.link.WebURLBuilderFactory.methodOn;

@Path("social/sites/site/connections/connection")
@ApplicationScoped
public class SiteConnectionResource implements FormResourceTemplate<SiteConnectionForm> {

    private final EntityManager entityManager;

    private final IdentityProvider identityProvider;

    @Inject
    public SiteConnectionResource(EntityManager entityManager, IdentityProvider identityProvider) {
        this.entityManager = entityManager;
        this.identityProvider = identityProvider;
    }

    public SiteConnectionResource() {
        this(null, null);
    }

    @Produces("application/json")
    @GET
    @Path("create")
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Create Site Connection")
    public SiteConnectionForm create(@QueryParam("to") UUID to) {
        SiteConnectionForm form = new SiteConnectionForm();

        ObjectMapper mapper = new ObjectMapper();

        form.setFrom(mapper.map(identityProvider.getUser(), UserSelect.class));
        form.setTo(mapper.map(entityManager.find(Site.class, to), SiteForm.class));

        linkTo(methodOn(SiteConnectionResource.class).save(new SiteConnectionForm()))
                .build(form::addLink);

        return form;
    }

    @Transactional
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Read Site Connection")
    @Override
    public SiteConnectionForm read(UUID id) {
        ObjectMapper mapper = new ObjectMapper();

        SiteConnection entity = entityManager.find(SiteConnection.class, id);
        SiteConnectionForm form = mapper.map(entity, SiteConnectionForm.class);

        linkTo(methodOn(SiteConnectionResource.class).update(id, new SiteConnectionForm()))
                .build(form::addLink);

        linkTo(methodOn(SiteConnectionResource.class).delete(id))
                .build(form::addLink);

        return form;
    }

    @Transactional
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Save Site Connection")
    @Override
    public SiteConnectionForm save(SiteConnectionForm form) {

        NewInstanceProvider instanceProvider = (uuid, sourceClass) -> entityManager.find(sourceClass, uuid);
        ObjectMapper mapper = new ObjectMapper(instanceProvider);
        SiteConnection entity = mapper.map(form, SiteConnection.class);

        entityManager.persist(entity);

        linkTo(methodOn(SiteConnectionResource.class).update(entity.getId(), new SiteConnectionForm()))
                .build(form::addLink);
        linkTo(methodOn(SiteConnectionResource.class).delete(entity.getId()))
                .build(form::addLink);

        return null;
    }

    @Transactional
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Update Site Connection")
    @Override
    public SiteConnectionForm update(UUID id, SiteConnectionForm form) {
        NewInstanceProvider instanceProvider = (uuid, sourceClass) -> entityManager.find(sourceClass, uuid);
        ObjectMapper mapper = new ObjectMapper(instanceProvider);
        mapper.map(form, SiteConnection.class);

        linkTo(methodOn(SiteConnectionResource.class).update(id, new SiteConnectionForm()))
                .build(form::addLink);

        linkTo(methodOn(SiteConnectionResource.class).delete(id))
                .build(form::addLink);

        return form;
    }

    @Transactional
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Delete Site Connection")
    @Override
    public ResponseOk delete(UUID id) {
        SiteConnection entity = entityManager.getReference(SiteConnection.class, id);
        entityManager.remove(entity);
        return new ResponseOk();
    }
}
