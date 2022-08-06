package com.anjunar.blomst.shared.users;

import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.common.rest.LinkDescription;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.security.User;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

@Path("shared/users")
@ApplicationScoped
public class UserSelectResource implements ListResourceTemplate<UserSelect, UserSelectSearch> {

    private final UserSelectService service;

    @Inject
    public UserSelectResource(UserSelectService service) {
        this.service = service;
    }

    public UserSelectResource() {
        this(null);
    }

    @Transactional
    @RolesAllowed({"Administrator", "User", "Guest"})
    @Override
    @LinkDescription("Table User Select")
    public Table<UserSelect> list(UserSelectSearch search) {

        List<User> users = service.find(search);
        long count = service.count(search);

        List<UserSelect> resources = new ArrayList<>();
        for (User user : users) {
            UserSelect resource = UserSelect.factory(user);
            resources.add(resource);
        }

        return new Table<>(resources, count) {};
    }

}
