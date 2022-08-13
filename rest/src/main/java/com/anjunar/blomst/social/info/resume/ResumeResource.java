package com.anjunar.blomst.social.info.resume;

import com.anjunar.blomst.social.sites.SitesResource;
import com.anjunar.blomst.social.sites.SitesSearch;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.control.users.Resume;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import java.util.UUID;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.*;

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

    @Transactional
    @LinkDescription("Read Resume")
    @Override
    public ResumeForm read(UUID id) {
        final Resume entity = entityManager.find(Resume.class, id);
        final ResumeForm form = ResumeForm.factory(entity);

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
        Resume entity = new Resume();
        ResumeForm.updater(form, entity, identityProvider, entityManager);
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
        final Resume entity = entityManager.find(Resume.class, id);
        ResumeForm.updater(form, entity, identityProvider, entityManager);

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
