package com.anjunar.blomst.social.pages.page;

import com.anjunar.blomst.control.users.UsersResource;
import com.anjunar.blomst.control.users.UsersSearch;
import com.anjunar.blomst.shared.users.UserSelectResource;
import com.anjunar.blomst.shared.users.UserSelectSearch;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.pages.PagesResource;
import com.anjunar.blomst.social.pages.PagesSearch;
import com.anjunar.blomst.social.pages.page.history.PageHistoryResource;
import com.anjunar.blomst.social.pages.page.history.PageHistorySearch;
import com.anjunar.blomst.social.pages.page.questions.QuestionsResource;
import com.anjunar.blomst.social.pages.page.questions.QuestionsSearch;
import com.anjunar.blomst.system.languages.LanguagesResource;
import com.anjunar.blomst.system.languages.LanguagesSearch;
import com.anjunar.common.rest.api.AbstractSchemaEntity;
import com.anjunar.common.rest.api.Form;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.rest.mapper.ResourceRestMapper;
import com.anjunar.common.rest.schema.schema.JsonArray;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.blomst.social.pages.Page;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
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

    private final IdentityManager identityManager;

    private final ResourceEntityMapper entityMapper;

    private final ResourceRestMapper restMapper;


    @Inject
    public PageResource(EntityManager entityManager, IdentityManager identityManager, ResourceEntityMapper entityMapper, ResourceRestMapper restMapper) {
        this.entityManager = entityManager;
        this.identityManager = identityManager;
        this.entityMapper = entityMapper;
        this.restMapper = restMapper;
    }

    public PageResource() {
        this(null, null, null, null);
    }

    @Produces("application/json")
    @GET
    @Path("create")
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Create Page")
    public Form<PageForm> create() {

        PageForm resource = new PageForm();
        Form<PageForm> form = new Form<>(resource) {};
        resource.setModifier(entityMapper.map(identityManager.getUser(), UserSelect.class, form, "modifier"));

        linkTo(methodOn(PageResource.class).save(new Form<>()))
                .build(form::addLink);

        JsonArray likes = form.find("likes", JsonArray.class);
        linkTo(methodOn(UserSelectResource.class).list(new UserSelectSearch()))
                .build(likes::addLink);

        JsonObject language = form.find("language", JsonObject.class);
        linkTo(methodOn(LanguagesResource.class).list(new LanguagesSearch()))
                .build(language::addLink);

        return form;
    }

    @Produces("application/json")
    @GET
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Read Page")
    public Form<PageForm> read(@QueryParam("id") UUID id, @QueryParam("revision") Integer revision) {

        Page page = entityManager.find(Page.class, id);
        Form<PageForm> resource = entityMapper.map(page, new Form<>() {});

        linkTo(methodOn(PageResource.class).update(page.getId(), new Form<>()))
                .build(resource::addLink);
        linkTo(methodOn(PageResource.class).delete(page.getId()))
                .build(resource::addLink);


        JsonObject language = resource.find("language", JsonObject.class);
        linkTo(methodOn(LanguagesResource.class).list(new LanguagesSearch()))
                .build(language::addLink);

        JsonArray likes = resource.find("likes", JsonArray.class);
        linkTo(methodOn(UserSelectResource.class).list(new UserSelectSearch()))
                .build(likes::addLink);

        JsonObject modifier = resource.find("modifier", JsonObject.class);
        linkTo(methodOn(UserSelectResource.class).list(new UserSelectSearch()))
                .build(modifier::addLink);

        QuestionsSearch search = new QuestionsSearch();
        search.setPage(id);
        linkTo(methodOn(QuestionsResource.class).list(search))
                .withRel("questions")
                .build(resource::addLink);

        PageHistorySearch historySearch = new PageHistorySearch();
        historySearch.setId(id.toString());
        linkTo(methodOn(PageHistoryResource.class).list(historySearch))
                .withRel("history")
                .build(resource::addLink);

        return resource;
    }

    @Consumes("application/json")
    @Produces("application/json")
    @POST
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Save Page")
    public ResponseOk save(Form<PageForm> resource) {

        Page page = restMapper.map(resource, Page.class);

        page.setModifier(identityManager.getUser());

        entityManager.persist(page);

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(PagesResource.class).list(new PagesSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    @Consumes("application/json")
    @Produces("application/json")
    @PUT
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Update Page")
    public ResponseOk update(@QueryParam("id") UUID id, Form<PageForm> resource) {

        Page page = restMapper.map(resource, Page.class);

        page.setModifier(identityManager.getUser());

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(PagesResource.class).list(new PagesSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    @DELETE
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Delete Page")
    @Produces("application/json")
    public ResponseOk delete(@QueryParam("id") UUID id) {
        Page page = entityManager.find(Page.class, id);

        List<Question> questions = entityManager.createQuery("select t from Question t where t.page.id = :page", Question.class)
                .setParameter("page", page.getId())
                .getResultList();

        for (Question question : questions) {
            entityManager.remove(question);
        }

        entityManager.remove(page);
        ResponseOk response = new ResponseOk();

        linkTo(methodOn(PagesResource.class).list(new PagesSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }
}
