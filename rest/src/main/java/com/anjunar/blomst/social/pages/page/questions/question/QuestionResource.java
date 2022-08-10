package com.anjunar.blomst.social.pages.page.questions.question;

import com.anjunar.blomst.control.users.UsersResource;
import com.anjunar.blomst.control.users.UsersSearch;
import com.anjunar.blomst.social.pages.page.questions.question.answers.AnswersResource;
import com.anjunar.blomst.social.pages.page.questions.question.answers.AnswersSearch;
import com.anjunar.common.rest.LinkDescription;
import com.anjunar.common.rest.MethodPredicate;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.api.json.schema.JsonArray;
import com.anjunar.common.rest.api.json.schema.JsonObject;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.social.pages.page.Question;
import com.anjunar.blomst.shared.users.user.UserSelect;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.anjunar.common.rest.WebURLBuilderFactory.linkTo;
import static com.anjunar.common.rest.WebURLBuilderFactory.methodOn;

@ApplicationScoped
@Path("pages/page/questions/question")
public class QuestionResource implements FormResourceTemplate<QuestionForm> {

    private final EntityManager entityManager;

    private final IdentityProvider identityProvider;

    @Inject
    public QuestionResource(EntityManager entityManager, IdentityProvider identityProvider) {
        this.entityManager = entityManager;
        this.identityProvider = identityProvider;
    }

    public QuestionResource() {
        this(null, null);
    }

    @Transactional
    @Produces("application/json")
    @GET
    @Path("create")
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Create Question")
    public QuestionForm create(@QueryParam("page") UUID page) {
        QuestionForm resource = new QuestionForm();

        resource.setPage(page);
        resource.setCreated(LocalDateTime.now());
        resource.setOwner(UserSelect.factory(identityProvider.getUser()));

        linkTo(methodOn(QuestionResource.class).save(new QuestionForm()))
                .build(resource::addLink);

        return resource;
    }

    @Transactional
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Read Question")
    public QuestionForm read(@QueryParam("id") UUID uuid) {
        Question question = entityManager.find(Question.class, uuid);

        QuestionForm resource = QuestionForm.factory(question);

        linkTo(methodOn(QuestionResource.class).update(question.getId(), new QuestionForm()))
                .build(resource::addLink);
        linkTo(methodOn(QuestionResource.class).delete(question.getId()))
                .build(resource::addLink);

        AnswersSearch search = new AnswersSearch();
        search.setTopic(uuid);
        linkTo(methodOn(AnswersResource.class).list(search))
                .withRel("answers")
                .build(resource::addLink);

        JsonObject owner = resource.find("owner", JsonObject.class);
        linkTo(methodOn(UsersResource.class).list(new UsersSearch()))
                .build(owner::addLink);

        JsonArray likes = resource.find("likes", JsonArray.class);
        linkTo(methodOn(UsersResource.class).list(new UsersSearch()))
                .build(likes::addLink);

        return resource;
    }

    @Override
    @Transactional
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Save Question")
    public QuestionForm save(QuestionForm resource) {

        UserSelect owner = resource.getOwner();
        if (!owner.getId().equals(identityProvider.getUser().getId())) {
            throw new NotAuthorizedException("Not Allowed");
        }

        Question question = new Question();

        QuestionForm.updater(resource, question, identityProvider, entityManager);

        entityManager.persist(question);

        resource.setId(question.getId());

        linkTo(methodOn(QuestionResource.class).update(question.getId(), new QuestionForm()))
                .build(resource::addLink);
        linkTo(methodOn(QuestionResource.class).delete(question.getId()))
                .build(resource::addLink);

        return resource;
    }

    @Override
    @Transactional
    @RolesAllowed({"Administrator", "User"})
    @MethodPredicate(QuestionOwnerPredicate.class)
    @LinkDescription("Update Question")
    public QuestionForm update(UUID id, QuestionForm resource) {

        Question question = entityManager.find(Question.class, id);

        QuestionForm.updater(resource, question, identityProvider, entityManager);

        linkTo(methodOn(QuestionResource.class).update(question.getId(), new QuestionForm()))
                .build(resource::addLink);
        linkTo(methodOn(QuestionResource.class).delete(question.getId()))
                .build(resource::addLink);

        return resource;
    }

    @Override
    @Transactional
    @RolesAllowed({"Administrator", "User"})
    @MethodPredicate(QuestionOwnerPredicate.class)
    @LinkDescription("Delete Question")
    public ResponseOk delete(UUID id) {
        Question question = entityManager.getReference(Question.class, id);

        entityManager.remove(question);
        return new ResponseOk();
    }

}