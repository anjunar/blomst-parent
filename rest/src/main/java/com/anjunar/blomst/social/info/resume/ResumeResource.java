package com.anjunar.blomst.social.info.resume;

import com.anjunar.blomst.control.users.Resume;
import com.anjunar.blomst.social.info.resume.event.EventForm;
import com.anjunar.blomst.social.info.resume.event.EventResource;
import com.anjunar.common.rest.MethodPredicate;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.security.IdentityManager;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.Path;

import java.util.ArrayList;
import java.util.List;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.linkTo;
import static com.anjunar.common.rest.link.WebURLBuilderFactory.methodOn;

@ApplicationScoped
@Path("social/info/resume")
public class ResumeResource implements ListResourceTemplate<EventForm, ResumeSearch> {

    private final ResumeService service;

    private final ResourceEntityMapper entityMapper;

    private final EntityManager entityManager;

    private final IdentityManager identityManager;

    @Inject
    public ResumeResource(ResumeService service, ResourceEntityMapper entityMapper, EntityManager entityManager, IdentityManager identityManager) {
        this.service = service;
        this.entityMapper = entityMapper;
        this.entityManager = entityManager;
        this.identityManager = identityManager;
    }

    public ResumeResource() {
        this(null, null, null, null);
    }

    @Override
    @RolesAllowed({"Administrator", "User"})
    public Table<EventForm> list(ResumeSearch search) {
        List<Resume> resumeList = service.find(search);
        long count = service.count(search);

        List<EventForm> resources = new ArrayList<>();

        for (Resume resume : resumeList) {
            EventForm form = entityMapper.map(resume, EventForm.class);

            linkTo(methodOn(EventResource.class).read(form.getId()))
                    .build(form::addLink);

            resources.add(form);
        }

        Table<EventForm> table = new Table<>(resources, count) {};

        linkTo(methodOn(EventResource.class).create())
                .build(table::addLink);

        return table;
    }
}
