package com.anjunar.blomst.social.pages.page;

import com.anjunar.blomst.control.users.UsersResource;
import com.anjunar.blomst.control.users.UsersSearch;
import com.anjunar.blomst.social.pages.page.history.PageHistoryResource;
import com.anjunar.blomst.social.pages.page.history.PageHistorySearch;
import com.anjunar.blomst.social.pages.page.questions.QuestionsResource;
import com.anjunar.blomst.social.pages.page.questions.QuestionsSearch;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.objectmapper.NewInstanceProvider;
import com.anjunar.common.rest.objectmapper.ResourceMapper;
import com.anjunar.common.rest.schema.schema.JsonArray;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.ApplicationResource;
import com.anjunar.blomst.social.pages.Page;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.linkTo;
import static com.anjunar.common.rest.link.WebURLBuilderFactory.methodOn;

@ApplicationScoped
@Path("pages/page")
public class PageResource {

    private final EntityManager entityManager;

    private final IdentityProvider identityProvider;

    @Inject
    public PageResource(EntityManager entityManager, IdentityProvider identityProvider) {
        this.entityManager = entityManager;
        this.identityProvider = identityProvider;
    }

    public PageResource() {
        this(null, null);
    }

    @Produces("application/json")
    @GET
    @Path("create")
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Create Page")
    public PageForm create() {

        PageForm pageForm = new PageForm();

        linkTo(methodOn(PageResource.class).save(new PageForm()))
                .build(pageForm::addLink);

        JsonArray likes = pageForm.find("likes", JsonArray.class);
        linkTo(methodOn(UsersResource.class).list(new UsersSearch()))
                .build(likes::addLink);

        JsonObject language = pageForm.find("language", JsonObject.class);
        linkTo(methodOn(ApplicationResource.class).language())
                .withRel("list")
                .build(language::addLink);

        JsonObject modifier = pageForm.find("modifier", JsonObject.class);
        linkTo(methodOn(UsersResource.class).list(new UsersSearch()))
                .build(modifier::addLink);

        return pageForm;
    }

    @Transactional
    @Produces("application/json")
    @GET
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Read Page")
    public PageForm read(@QueryParam("id") UUID id, @QueryParam("revision") Integer revision) {

        AuditReader auditReader = AuditReaderFactory.get(entityManager);

        if (revision == null) {
            revision = (Integer) auditReader.getRevisionNumberForDate(new Date(Long.MAX_VALUE));
        }

        Page page = auditReader.find(Page.class, id, revision);

        ResourceMapper mapper = new ResourceMapper();
        PageForm pageForm = mapper.map(page, PageForm.class);

        linkTo(methodOn(PageResource.class).update(page.getId(), new PageForm()))
                .build(pageForm::addLink);
        linkTo(methodOn(PageResource.class).delete(page.getId()))
                .build(pageForm::addLink);


        JsonObject language = pageForm.find("language", JsonObject.class);
        linkTo(methodOn(ApplicationResource.class).language())
                .withRel("list")
                .build(language::addLink);

        JsonArray likes = pageForm.find("likes", JsonArray.class);
        linkTo(methodOn(UsersResource.class).list(new UsersSearch()))
                .build(likes::addLink);

        JsonObject modifier = pageForm.find("modifier", JsonObject.class);
        linkTo(methodOn(UsersResource.class).list(new UsersSearch()))
                .build(modifier::addLink);

        QuestionsSearch search = new QuestionsSearch();
        search.setPage(id);
        linkTo(methodOn(QuestionsResource.class).list(search))
                .withRel("questions")
                .build(pageForm::addLink);

        PageHistorySearch historySearch = new PageHistorySearch();
        historySearch.setId(id);
        linkTo(methodOn(PageHistoryResource.class).list(historySearch))
                .withRel("history")
                .build(pageForm::addLink);

        return pageForm;
    }

    @Transactional
    @Consumes("application/json")
    @Produces("application/json")
    @POST
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Save Page")
    public PageForm save(PageForm resource) {

        NewInstanceProvider instanceProvider = (uuid, sourceClass) -> entityManager.find(sourceClass, uuid);
        ResourceMapper mapper = new ResourceMapper(instanceProvider);
        Page page = mapper.map(resource, Page.class);

        entityManager.persist(page);

        resource.setId(page.getId());

        linkTo(methodOn(PageResource.class).update(page.getId(), new PageForm()))
                .build(resource::addLink);
        linkTo(methodOn(PageResource.class).delete(page.getId()))
                .build(resource::addLink);

        return resource;
    }

    @Transactional
    @Consumes("application/json")
    @Produces("application/json")
    @PUT
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Update Page")
    public PageForm update(@QueryParam("id") UUID id, PageForm resource) {

        NewInstanceProvider instanceProvider = (uuid, sourceClass) -> entityManager.find(sourceClass, uuid);
        ResourceMapper mapper = new ResourceMapper(instanceProvider);
        PageForm page = mapper.map(resource, PageForm.class);

        linkTo(methodOn(PageResource.class).update(page.getId(), new PageForm()))
                .build(resource::addLink);
        linkTo(methodOn(PageResource.class).delete(page.getId()))
                .build(resource::addLink);

        return resource;
    }

    @Transactional
    @DELETE
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Delete Page")
    public ResponseOk delete(@QueryParam("id") UUID id) {
        Page page = entityManager.find(Page.class, id);

        List<Question> questions = entityManager.createQuery("select t from Question t where t.page.id = :page", Question.class)
                .setParameter("page", page.getId())
                .getResultList();

        for (Question question : questions) {
            entityManager.remove(question);
        }

        entityManager.remove(page);
        return new ResponseOk();
    }
}
