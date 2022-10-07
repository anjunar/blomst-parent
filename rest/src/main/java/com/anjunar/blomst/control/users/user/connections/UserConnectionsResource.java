package com.anjunar.blomst.control.users.user.connections;

import com.anjunar.blomst.control.users.user.connections.categories.CategoriesResource;
import com.anjunar.blomst.control.users.user.connections.categories.CategoriesSearch;
import com.anjunar.blomst.control.users.user.connections.connection.UserConnectionForm;
import com.anjunar.blomst.control.users.user.connections.connection.UserConnectionResource;
import com.anjunar.blomst.shared.users.UserSelectResource;
import com.anjunar.blomst.shared.users.UserSelectSearch;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.UserConnection;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.*;

@Path("control/users/user/connections")
public class UserConnectionsResource implements ListResourceTemplate<UserConnectionForm, UserConnectionsSearch> {

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
    public Table<UserConnectionForm> list(UserConnectionsSearch search) {

        final long count = service.count(search);
        final List<UserConnection> connections = service.find(search);
        final List<UserConnectionForm> resources = new ArrayList<>();

        List<UserConnection> accepted = service.accepted(search.getFrom());

        for (UserConnection connection : connections) {
            UserConnectionForm form = mapper.map(connection, UserConnectionForm.class);

            for (UserConnection acceptedConnection : accepted) {
                if (acceptedConnection.getFrom().equals(connection.getTo())) {
                    form.setAccepted(true);
                }
            }

            linkTo(methodOn(UserConnectionResource.class).read(form.getId()))
                    .build(form::addLink);

            resources.add(form);
        }

        Table<UserConnectionForm> table = new Table<>(resources, count) {};

        CategoriesSearch categoriesSearch = new CategoriesSearch();
        categoriesSearch.setOwner(identity.getUser().getId());
        linkTo(methodOn(CategoriesResource.class).list(categoriesSearch))
                .withRel("categories")
                .build(table::addLink);

        linkTo(methodOn(UserConnectionResource.class).create(null))
                .build(table::addLink);

        JsonObject from = table.find("from", JsonObject.class);
        linkTo(methodOn(UserSelectResource.class).list(new UserSelectSearch()))
                .build(from::addLink);
        JsonObject category = table.find("category", JsonObject.class);
        linkTo(methodOn(CategoriesResource.class).list(categoriesSearch))
                .build(category::addLink);
        JsonObject to = table.find("to", JsonObject.class);
        linkTo(methodOn(UserSelectResource.class).list(new UserSelectSearch()))
                .build(to::addLink);

        return table;
    }
}
