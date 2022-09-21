package com.anjunar.blomst.social.sites.site.connections;

import com.anjunar.blomst.shared.users.UserSelectResource;
import com.anjunar.blomst.shared.users.UserSelectSearch;
import com.anjunar.blomst.social.sites.SitesResource;
import com.anjunar.blomst.social.sites.SitesSearch;
import com.anjunar.blomst.social.sites.site.connections.connection.SiteConnectionForm;
import com.anjunar.blomst.social.sites.site.connections.connection.SiteConnectionResource;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.rest.api.Table;
import com.anjunar.blomst.social.sites.SiteConnection;

import com.anjunar.common.rest.mapper.ResourceEntityMapper;
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

@Path("social/sites/site/connections")
@ApplicationScoped
public class SiteConnectionsResource implements ListResourceTemplate<SiteConnectionForm, SiteConnectionsSearch> {

    private final SiteConnectionService service;

    private final ResourceEntityMapper mapper;


    @Inject
    public SiteConnectionsResource(SiteConnectionService service, ResourceEntityMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    public SiteConnectionsResource() {
        this(null, null);
    }

    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Table Site Connection")
    @Override
    public Table<SiteConnectionForm> list(SiteConnectionsSearch search) {
        final long count = service.count(search);
        final List<SiteConnection> connections = service.find(search);
        final List<SiteConnectionForm> resources = new ArrayList<>();

        for (SiteConnection connection : connections) {
            SiteConnectionForm form = mapper.map(connection, SiteConnectionForm.class);

            linkTo(methodOn(SiteConnectionResource.class).read(connection.getId()))
                    .build(form::addLink);

            resources.add(form);
        }

        Table<SiteConnectionForm> table = new Table<>(resources, count) {};

        linkTo(methodOn(SiteConnectionResource.class).create())
                .build(table::addLink);

        JsonObject from = table.find("from", JsonObject.class);
        linkTo(methodOn(UserSelectResource.class).list(new UserSelectSearch()))
                .build(from::addLink);

        JsonObject to = table.find("to", JsonObject.class);
        linkTo(methodOn(SitesResource.class).list(new SitesSearch()))
                .build(to::addLink);

        return table;
    }
}
