package com.anjunar.blomst.control.mail.template;

import com.anjunar.common.mail.Template;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.Editor;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
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

@Path("mail/templates/template")
@ApplicationScoped
public class TemplateResource implements FormResourceTemplate<TemplateForm> {

    private final EntityManager entityManager;

    private final IdentityProvider identityProvider;

    @Inject
    public TemplateResource(EntityManager entityManager, IdentityProvider identityProvider) {
        this.entityManager = entityManager;
        this.identityProvider = identityProvider;
    }

    public TemplateResource() {
        this(null, null);
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
        linkTo(methodOn(ApplicationResource.class).language())
                .withRel("list")
                .build(language::addLink);

        return resource;
    }

    @Override
    @Transactional
    @RolesAllowed("Administrator")
    @LinkDescription("Read Template")
    public TemplateForm read(UUID id) {

        Template template = entityManager.find(Template.class, id);

        TemplateForm resource = TemplateForm.factory(template);

        linkTo(methodOn(TemplateResource.class).update(template.getId(), new TemplateForm()))
                .build(resource::addLink);
        linkTo(methodOn(TemplateResource.class).delete(template.getId()))
                .build(resource::addLink);
        JsonObject language = resource.find("language", JsonObject.class);
        linkTo(methodOn(ApplicationResource.class).language())
                .withRel("list")
                .build(language::addLink);

        return resource;
    }

    @Override
    @Transactional
    @RolesAllowed("Administrator")
    @LinkDescription("Save Template")
    public TemplateForm save(TemplateForm form) {

        Template template = new Template();

        TemplateForm.updater(form, template, entityManager, identityProvider);

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

        Template template = entityManager.find(Template.class, id);

        TemplateForm.updater(form, template, entityManager, identityProvider);

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
