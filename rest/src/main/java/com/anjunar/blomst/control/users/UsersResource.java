package com.anjunar.blomst.control.users;

import com.anjunar.blomst.control.users.user.UserForm;
import com.anjunar.blomst.control.users.user.UserResource;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.security.User;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.*;

@ApplicationScoped
@Path("control/users")
public class UsersResource implements ListResourceTemplate<UserForm, UsersSearch> {

    private final UsersService service;

    @Inject
    public UsersResource(UsersService service) {
        this.service = service;
    }

    public UsersResource() {
        this(null);
    }

    @Transactional
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("User Table")
    public Table<UserForm> list(UsersSearch search) {

        List<User> users = service.find(search);
        long count = service.count(search);

        List<UserForm> resources = new ArrayList<>();
        for (User user : users) {
            UserForm resource = UserForm.factory(user);
            resources.add(resource);

            linkTo(methodOn(UserResource.class).read(user.getId()))
                    .build(resource::addLink);
        }

        Table<UserForm> table = new Table<>(resources, count) {};

        linkTo(methodOn(UserResource.class).create())
                .build(table::addLink);

        return table;

    }

}
