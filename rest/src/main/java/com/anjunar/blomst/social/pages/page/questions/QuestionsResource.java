package com.anjunar.blomst.social.pages.page.questions;

import com.anjunar.blomst.social.pages.page.questions.question.QuestionForm;
import com.anjunar.blomst.social.pages.page.questions.question.QuestionResource;
import com.anjunar.common.rest.LinkDescription;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.blomst.social.pages.page.Question;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

import static com.anjunar.common.rest.WebURLBuilderFactory.linkTo;
import static com.anjunar.common.rest.WebURLBuilderFactory.methodOn;

@ApplicationScoped
@Path("pages/page/questions")
public class QuestionsResource implements ListResourceTemplate<QuestionForm, QuestionsSearch> {

    private final QuestionsService service;

    @Inject
    public QuestionsResource(QuestionsService service) {
        this.service = service;
    }

    public QuestionsResource() {
        this(null);
    }

    @Override
    @Transactional
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Table Question")
    public Table<QuestionForm> list(QuestionsSearch search) {

        long count = service.count(search);
        List<Question> questions = service.find(search);

        List<QuestionForm> resources = new ArrayList<>();

        for (Question question : questions) {
            QuestionForm resource = QuestionForm.factory(question);

            resources.add(resource);

            linkTo(methodOn(QuestionResource.class).read(question.getId()))
                    .build(resource::addLink);

        }

        Table<QuestionForm> table = new Table<>(resources, count) {};

        linkTo(methodOn(QuestionResource.class).create(search.getPage()))
                .build(table::addLink);

        return table;
    }
}
