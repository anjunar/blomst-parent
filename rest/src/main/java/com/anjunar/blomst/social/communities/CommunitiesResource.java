package com.anjunar.blomst.social.communities;

import com.anjunar.blomst.social.communities.community.CommunityForm;
import com.anjunar.blomst.social.communities.community.CommunityResource;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.rest.api.Table;

import com.anjunar.common.rest.mapper.ResourceEntityMapper;
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
@Path("social/communities")
public class CommunitiesResource implements ListResourceTemplate<CommunityForm, CommunitiesSearch> {

    private final CommunitiesService service;

    private final ResourceEntityMapper mapper;


    @Inject
    public CommunitiesResource(CommunitiesService service, ResourceEntityMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    public CommunitiesResource() {
        this(null, null);
    }

    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Table Communities")
    @Override
    public Table<CommunityForm> list(CommunitiesSearch search) {

        final long count = service.count(search);
        final List<Community> communities = service.find(search);
        final List<CommunityForm> resources = new ArrayList<>();
        Table<CommunityForm> table = new Table<>(resources, count) {};


        for (final Community community : communities) {
            CommunityForm form = mapper.map(community, CommunityForm.class, table);

            linkTo(methodOn(CommunityResource.class).read(form.getId()))
                    .build(form::addLink);

            resources.add(form);
        }

        linkTo(methodOn(CommunityResource.class).create())
                .build(table::addLink);

        return table;
    }
}
