package com.anjunar.blomst.social.info.resume.event;

import com.anjunar.blomst.control.users.Resume;
import com.anjunar.blomst.control.users.user.connections.categories.CategoriesResource;
import com.anjunar.blomst.control.users.user.connections.categories.CategoriesSearch;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.info.resume.ResumeResource;
import com.anjunar.blomst.social.info.resume.ResumeSearch;
import com.anjunar.blomst.social.sites.SitesResource;
import com.anjunar.blomst.social.sites.SitesSearch;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.api.SecuredForm;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.rest.mapper.ResourceRestMapper;
import com.anjunar.common.rest.schema.schema.JsonArray;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.security.IdentityManager;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.linkTo;
import static com.anjunar.common.rest.link.WebURLBuilderFactory.methodOn;

@ApplicationScoped
@Path("social/info/resume/event")
public class EventResource implements FormResourceTemplate<SecuredForm<EventForm>> {

    private final EntityManager entityManager;

    private final IdentityManager identityManager;

    private final ResourceEntityMapper entityMapper;

    private final ResourceRestMapper restMapper;


    @Inject
    public EventResource(EntityManager entityManager, IdentityManager identityManager, ResourceEntityMapper entityMapper, ResourceRestMapper restMapper) {
        this.entityManager = entityManager;
        this.identityManager = identityManager;
        this.entityMapper = entityMapper;
        this.restMapper = restMapper;
    }

    public EventResource() {
        this(null, null, null, null);
    }

    @Path("create")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"Administrator", "User"})
    public SecuredForm<EventForm> create() {
        EventForm form = new EventForm();

        form.setOwner(entityMapper.map(identityManager.getUser(), UserSelect.class));

        SecuredForm<EventForm> securedForm = new SecuredForm<>() {};
        securedForm.setForm(form);

        linkTo(methodOn(EventResource.class).save(new SecuredForm<>()))
                .build(securedForm::addLink);

        JsonObject site = securedForm.find("site", JsonObject.class);
        linkTo(methodOn(SitesResource.class).list(new SitesSearch()))
                .build(site::addLink);

        JsonObject visibility = securedForm.find("form", JsonObject.class);
        linkTo(methodOn(CategoriesResource.class).list(new CategoriesSearch()))
                .build(visibility::addLink);

        return securedForm;
    }

    @LinkDescription("Read Resume")
    @Override
    @RolesAllowed({"Administrator", "User"})
    public SecuredForm<EventForm> read(UUID id) {
        final Resume entity = entityManager.find(Resume.class, id);

        SecuredForm<EventForm> securedForm = entityMapper.mapSecuredForm(entity, new SecuredForm<>() {});

        if (entity.getOwner().equals(identityManager.getUser())) {
            linkTo(methodOn(EventResource.class).update(entity.getId(), new SecuredForm<>()))
                    .build(securedForm::addLink);
            linkTo(methodOn(EventResource.class).delete(entity.getId()))
                    .build(securedForm::addLink);
        }

        JsonObject site = securedForm.find("site", JsonObject.class);
        linkTo(methodOn(SitesResource.class).list(new SitesSearch()))
                .build(site::addLink);

        JsonObject visibility = securedForm.find("form", JsonObject.class);
        linkTo(methodOn(CategoriesResource.class).list(new CategoriesSearch()))
                .build(visibility::addLink);

        return securedForm;
    }

    @LinkDescription("Save Resume")
    @Override
    @RolesAllowed({"Administrator", "User"})
    public ResponseOk save(SecuredForm<EventForm> form) {
        Resume entity = restMapper.mapSecuredForm(form, Resume.class);

        entityManager.persist(entity);

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(ResumeResource.class).list(new ResumeSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    @LinkDescription("Update Resume")
    @Override
    @RolesAllowed({"Administrator", "User"})
    public ResponseOk update(UUID id, SecuredForm<EventForm> form) {
        Resume entity = restMapper.mapSecuredForm(form, Resume.class);

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(ResumeResource.class).list(new ResumeSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    @LinkDescription("Delete Resume")
    @Override
    @RolesAllowed({"Administrator", "User"})
    public ResponseOk delete(UUID id) {
        Resume entity = entityManager.getReference(Resume.class, id);
        entityManager.remove(entity);
        ResponseOk response = new ResponseOk();

        linkTo(methodOn(ResumeResource.class).list(new ResumeSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }
}
