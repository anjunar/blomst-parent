package com.anjunar.blomst.social.pages.page.questions.question.answers;

import com.anjunar.blomst.social.pages.page.questions.question.answers.answer.AnswerForm;
import com.anjunar.blomst.social.pages.page.questions.question.answers.answer.AnswerResource;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.rest.objectmapper.ResourceMapper;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.blomst.social.pages.page.Answer;
import com.anjunar.blomst.shared.users.UserSelectResource;
import com.anjunar.blomst.shared.users.UserSelectSearch;

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
@Path("pages/page/questions/question/answers")
public class AnswersResource implements ListResourceTemplate<AnswerForm, AnswersSearch> {

    private final AnswersService service;

    @Inject
    public AnswersResource(AnswersService service) {
        this.service = service;
    }

    public AnswersResource() {
        this(null);
    }

    @Override
    @RolesAllowed({"Administrator", "User", "Guest"})
    @Transactional
    @LinkDescription("Table Answer")
    public Table<AnswerForm> list(AnswersSearch search) {

        long count = service.count(search);
        List<Answer> replies = service.find(search);

        List<AnswerForm> resources = new ArrayList<>();

        for (Answer answer : replies) {
            ResourceMapper mapper = new ResourceMapper();
            AnswerForm resource = mapper.map(answer, AnswerForm.class);

            resources.add(resource);

            linkTo(methodOn(AnswerResource.class).read(answer.getId()))
                    .build(resource::addLink);
        }

        Table<AnswerForm> table = new Table<>(resources, count) {};

        linkTo(methodOn(AnswerResource.class).create(search.getTopic()))
                .build(table::addLink);

        JsonObject owner = table.find("owner", JsonObject.class);
        linkTo(methodOn(UserSelectResource.class).list(new UserSelectSearch()))
                .build(owner::addLink);

        return table;
    }
}
