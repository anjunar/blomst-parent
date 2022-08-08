package com.anjunar.blomst.social.communities.community.connections;

import com.anjunar.blomst.social.communities.community.connections.connection.CommunityConnectionForm;
import com.anjunar.blomst.social.communities.community.connections.connection.CommunityConnectionResource;
import com.anjunar.common.rest.LinkDescription;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.rest.api.Table;
import com.anjunar.blomst.social.communities.CommunitiesConnection;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;

import java.util.ArrayList;
import java.util.List;

import static com.anjunar.common.rest.WebURLBuilderFactory.linkTo;
import static com.anjunar.common.rest.WebURLBuilderFactory.methodOn;

@ApplicationScoped
@Path("social/communities/community/connections")
public class CommunityConnectionsResource implements ListResourceTemplate<CommunityConnectionForm, CommunityConnectionsSearch> {

    private final CommunityConnectionsService service;

    @Inject
    public CommunityConnectionsResource(CommunityConnectionsService service) {
        this.service = service;
    }

    public CommunityConnectionsResource() {
        this(null);
    }

    @Override
    @Transactional
    @LinkDescription("Table Community Connection")
    public Table<CommunityConnectionForm> list(CommunityConnectionsSearch search) {
        final long count = service.count(search);
        final List<CommunitiesConnection> connections = service.find(search);
        final List<CommunityConnectionForm> resources = new ArrayList<>();

        for (CommunitiesConnection entity : connections) {
            CommunityConnectionForm form = CommunityConnectionForm.factory(entity);

            linkTo(methodOn(CommunityConnectionResource.class).read(entity.getId()))
                    .build(form::addLink);

            resources.add(form);
        }

        return new Table<>(resources, count) {};
    }
}
