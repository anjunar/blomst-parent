package com.anjunar.blomst.control.users;

import com.anjunar.blomst.control.roles.RolesResource;
import com.anjunar.blomst.control.roles.RolesSearch;
import com.anjunar.blomst.control.users.user.UserForm;
import com.anjunar.blomst.control.users.user.UserResource;
import com.anjunar.blomst.system.languages.LanguagesResource;
import com.anjunar.blomst.system.languages.LanguagesSearch;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.rest.schema.schema.JsonArray;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.security.User;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.*;

@ApplicationScoped
@Path("control/users")
public class UsersResource implements ListResourceTemplate<UserForm, UsersSearch> {

    private final UsersService service;

    private final ResourceEntityMapper mapper;


    @Inject
    public UsersResource(UsersService service, ResourceEntityMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    public UsersResource() {
        this(null, null);
    }

    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("User Table")
    public Table<UserForm> list(UsersSearch search) {
        long count = service.count(search);

        List<User> users = service.find(search);

        List<UserForm> resources = new ArrayList<>();
        Table<UserForm> table = new Table<>(resources, count) {};

        for (User user : users) {
            UserForm resource = mapper.map(user, UserForm.class, table);
            resources.add(resource);

            linkTo(methodOn(UserResource.class).read(user.getId()))
                    .build(resource::addLink);
        }

        linkTo(methodOn(UserResource.class).create())
                .build(table::addLink);

        JsonArray roles = table.find("roles", JsonArray.class);
        if (Objects.nonNull(roles)) {
            linkTo(methodOn(RolesResource.class).list(new RolesSearch()))
                    .build(roles::addLink);
        }

        JsonObject language = table.find("language", JsonObject.class);
        linkTo(methodOn(LanguagesResource.class).list(new LanguagesSearch()))
                .build(language::addLink);

        return table;
    }

}
