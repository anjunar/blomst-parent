package com.anjunar.blomst.system.mail.template;

import com.anjunar.blomst.social.sites.SitesResource;
import com.anjunar.blomst.social.sites.SitesSearch;
import com.anjunar.blomst.system.languages.LanguagesResource;
import com.anjunar.blomst.system.languages.LanguagesSearch;
import com.anjunar.blomst.system.mail.TemplatesResource;
import com.anjunar.blomst.system.mail.TemplatesSearch;
import com.anjunar.common.mail.Template;
import com.anjunar.common.rest.api.*;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.rest.mapper.ResourceRestMapper;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.security.IdentityManager;

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

@Path("system/mail/templates/template")
@ApplicationScoped
public class TemplateResource implements FormResourceTemplate<TemplateForm> {

    private final EntityManager entityManager;

    private final IdentityManager identityManager;

    private final ResourceEntityMapper entityMapper;

    private final ResourceRestMapper restMapper;


    @Inject
    public TemplateResource(EntityManager entityManager, IdentityManager identityManager, ResourceEntityMapper entityMapper, ResourceRestMapper restMapper) {
        this.entityManager = entityManager;
        this.identityManager = identityManager;
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
    public Form<TemplateForm> create() {
        TemplateForm resource = new TemplateForm();

        resource.setContent(new Editor());
        Form<TemplateForm> form = new Form<>(resource) {};

        linkTo(methodOn(TemplateResource.class).save(new TemplateForm()))
                .build(form::addLink);
        JsonObject language = form.find("language", JsonObject.class);
        linkTo(methodOn(LanguagesResource.class).list(new LanguagesSearch()))
                .build(language::addLink);


        return form;
    }

    @Override
    @RolesAllowed("Administrator")
    @LinkDescription("Read Template")
    public Form<TemplateForm> read(UUID id) {

        Template template = entityManager.find(Template.class, id);

        Form<TemplateForm> resource = entityMapper.map(template, new Form<>() {});

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
    @RolesAllowed("Administrator")
    @LinkDescription("Save Template")
    public ResponseOk save(TemplateForm form) {

        Template template = restMapper.map(form, Template.class);

        entityManager.persist(template);

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(TemplatesResource.class).list(new TemplatesSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    @Override
    @RolesAllowed("Administrator")
    @LinkDescription("Update Template")
    public ResponseOk update(UUID id, TemplateForm form) {

        restMapper.map(form, Template.class);

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(TemplatesResource.class).list(new TemplatesSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    @Override
    @RolesAllowed("Administrator")
    @LinkDescription("Delete Template")
    public ResponseOk delete(UUID id) {
        Template reference = entityManager.getReference(Template.class, id);
        entityManager.remove(reference);
        ResponseOk response = new ResponseOk();

        linkTo(methodOn(TemplatesResource.class).list(new TemplatesSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }
}
