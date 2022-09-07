package com.anjunar.blomst.system.mail.template;

import com.anjunar.blomst.system.languages.LanguagesResource;
import com.anjunar.blomst.system.languages.LanguagesSearch;
import com.anjunar.common.mail.Template;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.Editor;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.rest.mapper.ResourceRestMapper;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.ApplicationResource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import java.util.UUID;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.linkTo;
import static com.anjunar.common.rest.link.WebURLBuilderFactory.methodOn;

@Path("system/mail/templates/template")
@ApplicationScoped
public class TemplateResource implements FormResourceTemplate<TemplateForm> {

    private final EntityManager entityManager;

    private final IdentityProvider identityProvider;

    private final ResourceEntityMapper entityMapper;

    private final ResourceRestMapper restMapper;


    @Inject
    public TemplateResource(EntityManager entityManager, IdentityProvider identityProvider, ResourceEntityMapper entityMapper, ResourceRestMapper restMapper) {
        this.entityManager = entityManager;
        this.identityProvider = identityProvider;
        this.entityMapper = entityMapper;
        this.restMapper = restMapper;
    }

    public TemplateResource() {
        this(null, null, null, null);
    }

    @GET
    @Path("create")
    @Produces("application/json")
    @RolesAllowed("Administrator")
    @LinkDescription("Create Template")
    public TemplateForm create() {
        TemplateForm resource = new TemplateForm();

        resource.setContent(new Editor());

        linkTo(methodOn(TemplateResource.class).save(new TemplateForm()))
                .build(resource::addLink);
        JsonObject language = resource.find("language", JsonObject.class);
        linkTo(methodOn(LanguagesResource.class).list(new LanguagesSearch()))
                .build(language::addLink);

        return resource;
    }

    @Override
    @Transactional
    @RolesAllowed("Administrator")
    @LinkDescription("Read Template")
    public TemplateForm read(UUID id) {

        Template template = entityManager.find(Template.class, id);

        TemplateForm resource = entityMapper.map(template, TemplateForm.class);

        linkTo(methodOn(TemplateResource.class).update(template.getId(), new TemplateForm()))
                .build(resource::addLink);
        linkTo(methodOn(TemplateResource.class).delete(template.getId()))
                .build(resource::addLink);
        JsonObject language = resource.find("language", JsonObject.class);
        linkTo(methodOn(LanguagesResource.class).list(new LanguagesSearch()))
                .build(language::addLink);

        return resource;
    }

    @Override
    @Transactional
    @RolesAllowed("Administrator")
    @LinkDescription("Save Template")
    public TemplateForm save(TemplateForm form) {

        Template template = restMapper.map(form, Template.class);

        entityManager.persist(template);

        form.setId(template.getId());

        linkTo(methodOn(TemplateResource.class).update(template.getId(), new TemplateForm()))
                .build(form::addLink);
        linkTo(methodOn(TemplateResource.class).delete(template.getId()))
                .build(form::addLink);

        return form;
    }

    @Override
    @Transactional
    @RolesAllowed("Administrator")
    @LinkDescription("Update Template")
    public TemplateForm update(UUID id, TemplateForm form) {

        Template template = restMapper.map(form, Template.class);

        linkTo(methodOn(TemplateResource.class).update(template.getId(), new TemplateForm()))
                .build(form::addLink);
        linkTo(methodOn(TemplateResource.class).delete(template.getId()))
                .build(form::addLink);

        return form;
    }

    @Override
    @Transactional
    @RolesAllowed("Administrator")
    @LinkDescription("Delete Template")
    public ResponseOk delete(UUID id) {
        Template reference = entityManager.getReference(Template.class, id);
        entityManager.remove(reference);
        return new ResponseOk();
    }
}
