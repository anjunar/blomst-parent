package com.anjunar.blomst.social.info.resume;

import com.anjunar.blomst.control.users.Resume;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.sites.SitesResource;
import com.anjunar.blomst.social.sites.SitesSearch;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.rest.schemamapper.NewInstanceProvider;
import com.anjunar.common.rest.schemamapper.ResourceMapper;
import com.anjunar.common.security.IdentityProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.linkTo;
import static com.anjunar.common.rest.link.WebURLBuilderFactory.methodOn;

@ApplicationScoped
@Path("social/info/resume")
public class ResumeResource implements FormResourceTemplate<ResumeForm> {

    private final EntityManager entityManager;

    private final IdentityProvider identityProvider;

    @Inject
    public ResumeResource(EntityManager entityManager, IdentityProvider identityProvider) {
        this.entityManager = entityManager;
        this.identityProvider = identityProvider;
    }

    public ResumeResource() {
        this(null, null);
    }

    @Path("create")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ResumeForm create() {
        ResumeForm form = new ResumeForm();

        ResourceMapper mapper = new ResourceMapper();

        form.setOwner(mapper.map(identityProvider.getUser(), UserSelect.class));

        linkTo(methodOn(ResumeResource.class).save(new ResumeForm()))
                .build(form::addLink);

        JsonObject site = form.find("site", JsonObject.class);
        linkTo(methodOn(SitesResource.class).list(new SitesSearch()))
                .build(site::addLink);

        return form;
    }

    @Transactional
    @LinkDescription("Read Resume")
    @Override
    public ResumeForm read(UUID id) {
        final Resume entity = entityManager.find(Resume.class, id);

        ResourceMapper mapper = new ResourceMapper();
        ResumeForm form = mapper.map(entity, ResumeForm.class);

        linkTo(methodOn(ResumeResource.class).update(entity.getId(), new ResumeForm()))
                .build(form::addLink);
        linkTo(methodOn(ResumeResource.class).delete(entity.getId()))
                .build(form::addLink);

        JsonObject site = form.find("site", JsonObject.class);
        linkTo(methodOn(SitesResource.class).list(new SitesSearch()))
                .build(site::addLink);

        return form;
    }

    @Transactional
    @LinkDescription("Save Resume")
    @Override
    public ResumeForm save(ResumeForm form) {

        NewInstanceProvider instanceProvider = (uuid, sourceClass) -> entityManager.find(sourceClass, uuid);
        ResourceMapper mapper = new ResourceMapper(instanceProvider);
        Resume entity = mapper.map(form, Resume.class);

        entityManager.persist(entity);

        linkTo(methodOn(ResumeResource.class).update(entity.getId(), new ResumeForm()))
                .build(form::addLink);
        linkTo(methodOn(ResumeResource.class).delete(entity.getId()))
                .build(form::addLink);

        return form;
    }

    @Transactional
    @LinkDescription("Update Resume")
    @Override
    public ResumeForm update(UUID id, ResumeForm form) {
        NewInstanceProvider instanceProvider = (uuid, sourceClass) -> entityManager.find(sourceClass, uuid);
        ResourceMapper mapper = new ResourceMapper(instanceProvider);
        Resume entity = mapper.map(form, Resume.class);

        linkTo(methodOn(ResumeResource.class).update(entity.getId(), new ResumeForm()))
                .build(form::addLink);
        linkTo(methodOn(ResumeResource.class).delete(entity.getId()))
                .build(form::addLink);

        return form;
    }

    @Transactional
    @LinkDescription("Delete Resume")
    @Override
    public ResponseOk delete(UUID id) {
        Resume entity = entityManager.getReference(Resume.class, id);
        entityManager.remove(entity);
        return new ResponseOk();
    }
}
