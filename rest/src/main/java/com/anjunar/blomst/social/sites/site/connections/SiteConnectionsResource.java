package com.anjunar.blomst.social.sites.site.connections;

import com.anjunar.blomst.social.sites.site.connections.connection.SiteConnectionForm;
import com.anjunar.blomst.social.sites.site.connections.connection.SiteConnectionResource;
import com.anjunar.common.rest.LinkDescription;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.rest.api.Table;
import com.anjunar.blomst.social.sites.SiteConnection;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

import static com.anjunar.common.rest.WebURLBuilderFactory.linkTo;
import static com.anjunar.common.rest.WebURLBuilderFactory.methodOn;

@Path("social/sites/site/connections")
@ApplicationScoped
public class SiteConnectionsResource implements ListResourceTemplate<SiteConnectionForm, SiteConnectionsSearch> {

    private final SiteConnectionService service;

    @Inject
    public SiteConnectionsResource(SiteConnectionService service) {
        this.service = service;
    }

    public SiteConnectionsResource() {
        this(null);
    }

    @Transactional
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Table Site Connection")
    @Override
    public Table<SiteConnectionForm> list(SiteConnectionsSearch search) {
        final long count = service.count(search);
        final List<SiteConnection> connections = service.find(search);
        final List<SiteConnectionForm> resources = new ArrayList<>();

        for (SiteConnection connection : connections) {
            SiteConnectionForm form = SiteConnectionForm.factory(connection);

            linkTo(methodOn(SiteConnectionResource.class).read(connection.getId()))
                    .build(form::addLink);

            resources.add(form);
        }

        return new Table<>(resources, count) {};
    }
}
