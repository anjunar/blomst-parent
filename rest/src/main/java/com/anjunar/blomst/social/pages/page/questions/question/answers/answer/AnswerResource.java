package com.anjunar.blomst.social.pages.page.questions.question.answers.answer;

import com.anjunar.blomst.shared.users.UserSelectResource;
import com.anjunar.blomst.shared.users.UserSelectSearch;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.pages.page.Answer;
import com.anjunar.blomst.social.pages.page.Question;
import com.anjunar.blomst.social.pages.page.questions.question.QuestionForm;
import com.anjunar.blomst.social.pages.page.questions.question.answers.AnswersResource;
import com.anjunar.blomst.social.pages.page.questions.question.answers.AnswersSearch;
import com.anjunar.common.rest.MethodPredicate;
import com.anjunar.common.rest.api.*;
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
import jakarta.ws.rs.QueryParam;

import java.util.UUID;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.linkTo;
import static com.anjunar.common.rest.link.WebURLBuilderFactory.methodOn;

@ApplicationScoped
@Path("pages/page/questions/question/answers/answer")
public class AnswerResource implements FormResourceTemplate<Form<AnswerForm>> {

    private final EntityManager entityManager;

    private final IdentityManager identityManager;

    private final ResourceEntityMapper entityMapper;

    private final ResourceRestMapper restMapper;


    @Inject
    public AnswerResource(EntityManager entityManager, IdentityManager identityManager, ResourceEntityMapper entityMapper, ResourceRestMapper restMapper) {
        this.entityManager = entityManager;
        this.identityManager = identityManager;
        this.entityMapper = entityMapper;
        this.restMapper = restMapper;
    }

    public AnswerResource() {
        this(null, null, null, null);
    }

    @Produces("application/json")
    @GET
    @Path("create")
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Create Answer")
    public Form<AnswerForm> create(@QueryParam("topic") UUID uuid) {
        AnswerForm resource = new AnswerForm();

        Form<AnswerForm> form = new Form<>(resource) {};
        form.dirty("question", "owner");

        Question question = entityManager.find(Question.class, uuid);

        resource.setQuestion(entityMapper.map(question, QuestionForm.class));
        resource.setOwner(entityMapper.map(identityManager.getUser(), UserSelect.class));

        resource.setEditor(new Editor());
        resource.setViews(0);

        linkTo(methodOn(AnswerResource.class).save(new Form<>()))
                .build(form::addLink);

        JsonObject owner = form.find("owner", JsonObject.class);
        linkTo(methodOn(UserSelectResource.class).list(new UserSelectSearch()))
                .build(owner::addLink);

        JsonArray likes = form.find("likes", JsonArray.class);
        linkTo(methodOn(UserSelectResource.class).list(new UserSelectSearch()))
                .build(likes::addLink);

        return form;
    }

    @Override
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Read Answer")
    public Form<AnswerForm> read(UUID id) {

        Answer answer = entityManager.find(Answer.class, id);

        Form<AnswerForm> resource = entityMapper.map(answer, new Form<>() {});

        linkTo(methodOn(AnswerResource.class).update(answer.getId(), new Form<>()))
                .build(resource::addLink);
        linkTo(methodOn(AnswerResource.class).delete(answer.getId()))
                .build(resource::addLink);

        JsonObject owner = resource.find("owner", JsonObject.class);
        linkTo(methodOn(UserSelectResource.class).list(new UserSelectSearch()))
                .build(owner::addLink);
        JsonArray likes = resource.find("likes", JsonArray.class);
        linkTo(methodOn(UserSelectResource.class).list(new UserSelectSearch()))
                .build(likes::addLink);

        return resource;
    }

    @Override
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Save Answer")
    public ResponseOk save(Form<AnswerForm> resource) {

        Answer answer = restMapper.map(resource, Answer.class);
        answer.setOwner(identityManager.getUser());

        entityManager.persist(answer);

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(AnswersResource.class).list(new AnswersSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    @Override
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Update Answer")
    @MethodPredicate(AnswerOwnerPredicate.class)
    public ResponseOk update(UUID id, Form<AnswerForm> resource) {

        Answer entity = restMapper.map(resource, Answer.class);
        entity.setOwner(identityManager.getUser());

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(AnswersResource.class).list(new AnswersSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    @Override
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Delete Answer")
    @MethodPredicate(AnswerOwnerPredicate.class)
    public ResponseOk delete(UUID id) {
        Answer answer = entityManager.getReference(Answer.class, id);
        entityManager.remove(answer);
        ResponseOk response = new ResponseOk();

        linkTo(methodOn(AnswersResource.class).list(new AnswersSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }
}
