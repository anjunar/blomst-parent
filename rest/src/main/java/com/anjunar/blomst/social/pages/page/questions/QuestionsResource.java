package com.anjunar.blomst.social.pages.page.questions;

import com.anjunar.blomst.shared.users.UserSelectResource;
import com.anjunar.blomst.shared.users.UserSelectSearch;
import com.anjunar.blomst.social.pages.PagesResource;
import com.anjunar.blomst.social.pages.PagesSearch;
import com.anjunar.blomst.social.pages.page.questions.question.QuestionForm;
import com.anjunar.blomst.social.pages.page.questions.question.QuestionResource;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.blomst.social.pages.page.Question;

import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.rest.schema.schema.JsonArray;
import com.anjunar.common.rest.schema.schema.JsonObject;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.linkTo;
import static com.anjunar.common.rest.link.WebURLBuilderFactory.methodOn;

@ApplicationScoped
@Path("pages/page/questions")
public class QuestionsResource implements ListResourceTemplate<QuestionForm, QuestionsSearch> {

    private final QuestionsService service;

    private final ResourceEntityMapper mapper;


    @Inject
    public QuestionsResource(QuestionsService service, ResourceEntityMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    public QuestionsResource() {
        this(null, null);
    }

    @Override
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Table Question")
    public Table<QuestionForm> list(QuestionsSearch search) {

        long count = service.count(search);
        List<Question> questions = service.find(search);

        List<QuestionForm> resources = new ArrayList<>();
        Table<QuestionForm> table = new Table<>(resources, count) {};

        for (Question question : questions) {
            QuestionForm resource = mapper.map(question, QuestionForm.class, table);

            resources.add(resource);

            linkTo(methodOn(QuestionResource.class).read(question.getId()))
                    .build(resource::addLink);

        }

        linkTo(methodOn(QuestionResource.class).create(search.getPage()))
                .build(table::addLink);

        JsonArray likes = table.find("likes", JsonArray.class);
        linkTo(methodOn(UserSelectResource.class).list(new UserSelectSearch()))
                .build(likes::addLink);

        JsonObject page = table.find("page", JsonObject.class);
        linkTo(methodOn(PagesResource.class).list(new PagesSearch()))
                .build(page::addLink);

        JsonObject owner = table.find("owner", JsonObject.class);
        linkTo(methodOn(UserSelectResource.class).list(new UserSelectSearch()))
                .build(owner::addLink);

        return table;
    }
}
