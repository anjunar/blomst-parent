package com.anjunar.blomst.social.communities.community.connections;

import com.anjunar.blomst.social.communities.community.connections.connection.CommunityConnectionForm;
import com.anjunar.blomst.social.communities.community.connections.connection.CommunityConnectionResource;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.rest.api.Table;
import com.anjunar.blomst.social.communities.CommunitiesConnection;

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
@Path("social/communities/community/connections")
public class CommunityConnectionsResource implements ListResourceTemplate<CommunityConnectionForm, CommunityConnectionsSearch> {

    private final CommunityConnectionsService service;

    private final ResourceEntityMapper mapper;


    @Inject
    public CommunityConnectionsResource(CommunityConnectionsService service, ResourceEntityMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    public CommunityConnectionsResource() {
        this(null, null);
    }

    @Override
    @LinkDescription("Table Community Connection")
    @RolesAllowed({"Administrator", "User"})
    public Table<CommunityConnectionForm> list(CommunityConnectionsSearch search) {
        final long count = service.count(search);
        final List<CommunitiesConnection> connections = service.find(search);
        final List<CommunityConnectionForm> resources = new ArrayList<>();

        for (CommunitiesConnection entity : connections) {
            CommunityConnectionForm form = mapper.map(entity, CommunityConnectionForm.class);

            linkTo(methodOn(CommunityConnectionResource.class).read(entity.getId()))
                    .build(form::addLink);

            resources.add(form);
        }

        return new Table<>(resources, count) {};
    }
}
