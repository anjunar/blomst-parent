package com.anjunar.blomst.social.pages.page.questions.question.answers;

import com.anjunar.blomst.shared.users.UserSelectResource;
import com.anjunar.blomst.shared.users.UserSelectSearch;
import com.anjunar.blomst.social.pages.page.questions.QuestionsResource;
import com.anjunar.blomst.social.pages.page.questions.QuestionsSearch;
import com.anjunar.blomst.social.pages.page.questions.question.answers.answer.AnswerForm;
import com.anjunar.blomst.social.pages.page.questions.question.answers.answer.AnswerResource;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.rest.schema.schema.JsonArray;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.blomst.social.pages.page.Answer;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.linkTo;
import static com.anjunar.common.rest.link.WebURLBuilderFactory.methodOn;

@ApplicationScoped
@Path("pages/page/questions/question/answers")
public class AnswersResource implements ListResourceTemplate<AnswerForm, AnswersSearch> {

    private final AnswersService service;

    private final ResourceEntityMapper mapper;


    @Inject
    public AnswersResource(AnswersService service, ResourceEntityMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    public AnswersResource() {
        this(null, null);
    }

    @Override
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Table Answer")
    public Table<AnswerForm> list(AnswersSearch search) {

        long count = service.count(search);
        List<Answer> replies = service.find(search);

        List<AnswerForm> resources = new ArrayList<>();

        for (Answer answer : replies) {
            AnswerForm resource = mapper.map(answer, AnswerForm.class);

            resources.add(resource);

            linkTo(methodOn(AnswerResource.class).read(answer.getId()))
                    .build(resource::addLink);
        }

        Table<AnswerForm> table = new Table<>(resources, count) {};

        linkTo(methodOn(AnswerResource.class).create(search.getQuestion()))
                .build(table::addLink);

        JsonArray likes = table.find("likes", JsonArray.class);
        linkTo(methodOn(UserSelectResource.class).list(new UserSelectSearch()))
                .build(likes::addLink);

        JsonObject owner = table.find("owner", JsonObject.class);
        linkTo(methodOn(UserSelectResource.class).list(new UserSelectSearch()))
                .build(owner::addLink);

        JsonObject question = table.find("question", JsonObject.class);
        linkTo(methodOn(QuestionsResource.class).list(new QuestionsSearch()))
                .build(question::addLink);

        return table;
    }
}
