package com.anjunar.blomst.social.info.resume;

import com.anjunar.blomst.control.users.Resume;
import com.anjunar.blomst.control.users.user.UserForm;
import com.anjunar.blomst.social.sites.SitesResource;
import com.anjunar.blomst.social.sites.SitesSearch;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.rest.mapper.ResourceRestMapper;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.security.IdentityManager;
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
@Path("social/info/resume")
public class ResumeResource implements FormResourceTemplate<ResumeForm> {

    private final EntityManager entityManager;

    private final IdentityManager identityManager;

    private final ResourceEntityMapper entityMapper;

    private final ResourceRestMapper restMapper;


    @Inject
    public ResumeResource(EntityManager entityManager, IdentityManager identityManager, ResourceEntityMapper entityMapper, ResourceRestMapper restMapper) {
        this.entityManager = entityManager;
        this.identityManager = identityManager;
        this.entityMapper = entityMapper;
        this.restMapper = restMapper;
    }

    public ResumeResource() {
        this(null, null, null, null);
    }

    @Path("create")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ResumeForm create() {
        ResumeForm form = new ResumeForm();

        form.setOwner(entityMapper.map(identityManager.getUser(), UserForm.class));

        linkTo(methodOn(ResumeResource.class).save(new ResumeForm()))
                .build(form::addLink);

        JsonObject site = form.find("site", JsonObject.class);
        linkTo(methodOn(SitesResource.class).list(new SitesSearch()))
                .build(site::addLink);

        return form;
    }

    @LinkDescription("Read Resume")
    @Override
    public ResumeForm read(UUID id) {
        final Resume entity = entityManager.find(Resume.class, id);

        ResumeForm form = entityMapper.map(entity, ResumeForm.class);

        if (entity.getOwner().equals(identityManager.getUser())) {
            linkTo(methodOn(ResumeResource.class).update(entity.getId(), new ResumeForm()))
                    .build(form::addLink);
            linkTo(methodOn(ResumeResource.class).delete(entity.getId()))
                    .build(form::addLink);
        }

        JsonObject site = form.find("site", JsonObject.class);
        if (site != null) {
            linkTo(methodOn(SitesResource.class).list(new SitesSearch()))
                    .build(site::addLink);
        }

        return form;
    }

    @LinkDescription("Save Resume")
    @Override
    public ResumeForm save(ResumeForm form) {
        Resume entity = restMapper.map(form, Resume.class);

        entityManager.persist(entity);

        linkTo(methodOn(ResumeResource.class).update(entity.getId(), new ResumeForm()))
                .build(form::addLink);
        linkTo(methodOn(ResumeResource.class).delete(entity.getId()))
                .build(form::addLink);

        return form;
    }

    @LinkDescription("Update Resume")
    @Override
    public ResumeForm update(UUID id, ResumeForm form) {
        Resume entity = restMapper.map(form, Resume.class);

        linkTo(methodOn(ResumeResource.class).update(entity.getId(), new ResumeForm()))
                .build(form::addLink);
        linkTo(methodOn(ResumeResource.class).delete(entity.getId()))
                .build(form::addLink);

        return form;
    }

    @LinkDescription("Delete Resume")
    @Override
    public ResponseOk delete(UUID id) {
        Resume entity = entityManager.getReference(Resume.class, id);
        entityManager.remove(entity);
        return new ResponseOk();
    }
}
