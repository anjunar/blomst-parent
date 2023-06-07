package com.anjunar.blomst.social.pages.page.questions.question;

import com.anjunar.blomst.shared.users.UserSelectResource;
import com.anjunar.blomst.shared.users.UserSelectSearch;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.pages.Page;
import com.anjunar.blomst.social.pages.page.PageForm;
import com.anjunar.blomst.social.pages.page.PageReference;
import com.anjunar.blomst.social.pages.page.questions.QuestionsResource;
import com.anjunar.blomst.social.pages.page.questions.QuestionsSearch;
import com.anjunar.blomst.social.pages.page.questions.question.answers.AnswersResource;
import com.anjunar.blomst.social.pages.page.questions.question.answers.AnswersSearch;
import com.anjunar.common.rest.api.Form;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.MethodPredicate;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.rest.mapper.ResourceRestMapper;
import com.anjunar.common.rest.schema.schema.JsonArray;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.blomst.social.pages.page.Question;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.linkTo;
import static com.anjunar.common.rest.link.WebURLBuilderFactory.methodOn;

@ApplicationScoped
@Path("pages/page/questions/question")
public class QuestionResource implements FormResourceTemplate<QuestionForm> {

    private final EntityManager entityManager;

    private final IdentityManager identityManager;

    private final ResourceEntityMapper entityMapper;

    private final ResourceRestMapper restMapper;


    @Inject
    public QuestionResource(EntityManager entityManager, IdentityManager identityManager, ResourceEntityMapper entityMapper, ResourceRestMapper restMapper) {
        this.entityManager = entityManager;
        this.identityManager = identityManager;
        this.entityMapper = entityMapper;
        this.restMapper = restMapper;
    }

    public QuestionResource() {
        this(null, null, null, null);
    }

    @Produces("application/json")
    @GET
    @Path("create")
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Create Question")
    public Form<QuestionForm> create(@QueryParam("page") UUID page) {
        QuestionForm resource = new QuestionForm();
        Form<QuestionForm> form = new Form<>(resource) {};
        form.dirty("page", "owner");

        resource.setCreated(LocalDateTime.now());
        resource.setModified(LocalDateTime.now());

        Page pageEntity = entityManager.find(Page.class, page);
        PageReference pageReference = new PageReference();
        pageReference.setId(pageEntity.getId());
        resource.setPage(pageReference);

        linkTo(methodOn(QuestionResource.class).save(new QuestionForm()))
                .build(form::addLink);

        return form;
    }

    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Read Question")
    public Form<QuestionForm> read(@QueryParam("id") UUID uuid) {
        Question question = entityManager.find(Question.class, uuid);

        Form<QuestionForm> resource = entityMapper.map(question, new Form<>() {});

        linkTo(methodOn(QuestionResource.class).update(question.getId(), new QuestionForm()))
                .build(resource::addLink);
        linkTo(methodOn(QuestionResource.class).delete(question.getId()))
                .build(resource::addLink);

        AnswersSearch search = new AnswersSearch();
        search.setQuestion(uuid);
        linkTo(methodOn(AnswersResource.class).list(search))
                .withRel("answers")
                .build(resource::addLink);

        JsonObject owner = resource.find("owner", JsonObject.class);
        linkTo(methodOn(UserSelectResource.class).list(new UserSelectSearch()))
                .build(owner::addLink);

        return resource;
    }

    @Override
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Save Question")
    public QuestionForm save(QuestionForm resource) {

        Question question = restMapper.map(resource, Question.class);
        question.setOwner(identityManager.getUser());

        entityManager.persist(question);
        resource.setId(question.getId());

        linkTo(methodOn(QuestionsResource.class).list(new QuestionsSearch()))
                .withRel("redirect")
                .build(resource::addLink);

        return resource;
    }

    @Override
    @RolesAllowed({"Administrator", "User"})
    @MethodPredicate(QuestionOwnerPredicate.class)
    @LinkDescription("Update Question")
    public QuestionForm update(UUID id, QuestionForm resource) {

        Question entity = restMapper.map(resource, Question.class);
        entity.setOwner(identityManager.getUser());

        linkTo(methodOn(QuestionsResource.class).list(new QuestionsSearch()))
                .withRel("redirect")
                .build(resource::addLink);

        return resource;
    }

    @Override
    @RolesAllowed({"Administrator", "User"})
    @MethodPredicate(QuestionOwnerPredicate.class)
    @LinkDescription("Delete Question")
    public ResponseOk delete(UUID id) {
        Question question = entityManager.getReference(Question.class, id);

        entityManager.remove(question);
        ResponseOk response = new ResponseOk();

        linkTo(methodOn(QuestionsResource.class).list(new QuestionsSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

}
