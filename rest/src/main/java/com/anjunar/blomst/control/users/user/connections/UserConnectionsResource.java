package com.anjunar.blomst.control.users.user.connections;

import com.anjunar.blomst.control.users.user.connections.categories.CategoriesResource;
import com.anjunar.blomst.control.users.user.connections.categories.CategoriesSearch;
import com.anjunar.blomst.control.users.user.connections.connection.UserConnectionResource;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.control.users.UserConnection;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.*;

@Path("control/users/user/connections")
public class UserConnectionsResource implements ListResourceTemplate<ConnectionRow, UserConnectionsSearch> {

    private final UserConnectionsService service;

    private final IdentityProvider identity;

    @Inject
    public UserConnectionsResource(UserConnectionsService service, IdentityProvider identity) {
        this.service = service;
        this.identity = identity;
    }

    public UserConnectionsResource() {
        this(null, null);
    }

    @Override
    @Transactional
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Table Connection")
    public Table<ConnectionRow> list(UserConnectionsSearch search) {

        final long count = service.count(search);
        final List<UserConnection> connections = service.find(search);
        final List<ConnectionRow> resources = new ArrayList<>();

        List<UserConnection> accepted = service.accepted(search.getFrom());

        for (UserConnection connection : connections) {
            ConnectionRow form = ConnectionRow.factory(connection);

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
        categoriesSearch.setOwner(identity.getUser().getId());
        linkTo(methodOn(CategoriesResource.class).list(categoriesSearch))
                .withRel("categories")
                .build(table::addLink);

        return table;
    }
}
