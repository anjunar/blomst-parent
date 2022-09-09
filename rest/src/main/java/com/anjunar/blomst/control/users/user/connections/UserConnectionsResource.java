package com.anjunar.blomst.control.users.user.connections;

import com.anjunar.blomst.control.users.user.connections.categories.CategoriesResource;
import com.anjunar.blomst.control.users.user.connections.categories.CategoriesSearch;
import com.anjunar.blomst.control.users.user.connections.connection.UserConnectionResource;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.UserConnection;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.*;

@Path("control/users/user/connections")
public class UserConnectionsResource implements ListResourceTemplate<ConnectionRow, UserConnectionsSearch> {

    private final UserConnectionsService service;

    private final IdentityManager identity;

    private final ResourceEntityMapper mapper;


    @Inject
    public UserConnectionsResource(UserConnectionsService service, IdentityManager identity, ResourceEntityMapper mapper) {
        this.service = service;
        this.identity = identity;
        this.mapper = mapper;
    }

    public UserConnectionsResource() {
        this(null, null, null);
    }

    @Override
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Table Connection")
    public Table<ConnectionRow> list(UserConnectionsSearch search) {

        final long count = service.count(search);
        final List<UserConnection> connections = service.find(search);
        final List<ConnectionRow> resources = new ArrayList<>();

        List<UserConnection> accepted = service.accepted(search.getFrom());

        for (UserConnection connection : connections) {
            ConnectionRow form = mapper.map(connection, ConnectionRow.class);

            for (UserConnection acceptedConnection : accepted) {
                if (acceptedConnection.getFrom().equals(connection.getTo())) {
                    form.setAccepted(true);
                }
            }

            linkTo(methodOn(UserConnectionResource.class).read(form.getId()))
                    .build(form::addLink);

            resources.add(form);
        }

        Table<ConnectionRow> table = new Table<>(resources, count) {};

        CategoriesSearch categoriesSearch = new CategoriesSearch();
        categoriesSearch.setOwner(search.getFrom());
        linkTo(methodOn(CategoriesResource.class).list(categoriesSearch))
                .withRel("categories")
                .build(table::addLink);

        linkTo(methodOn(UserConnectionResource.class).create(search.getTo()))
                .build(table::addLink);

        return table;
    }
}
