package com.anjunar.blomst.shared.users;

import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.security.User;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

@Path("shared/users")
@ApplicationScoped
public class UserSelectResource implements ListResourceTemplate<UserSelect, UserSelectSearch> {

    private final UserSelectService service;

    private final ResourceEntityMapper entityMapper;

    @Inject
    public UserSelectResource(UserSelectService service, ResourceEntityMapper entityMapper) {
        this.service = service;
        this.entityMapper = entityMapper;
    }

    public UserSelectResource() {
        this(null, null);
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
            UserSelect resource = entityMapper.map(user, UserSelect.class);
            resources.add(resource);
        }

        return new Table<>(resources, count) {};
    }

}
