package com.anjunar.blomst.social.chat;

import com.anjunar.blomst.WebSocketSessions;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.security.IdentityStore;
import com.anjunar.common.security.User;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;

import java.util.ArrayList;
import java.util.List;

@Path("social/chat/online")
public class OnlineUsersResource implements ListResourceTemplate<OnlineUserForm, OnlineUsersSearch> {

    private final OnlineUsersService service;

    private final IdentityStore identityStore;

    private final ResourceEntityMapper entityMapper;

    private final WebSocketSessions sessions;

    @Inject
    public OnlineUsersResource(OnlineUsersService service, IdentityStore identityStore, ResourceEntityMapper entityMapper, WebSocketSessions sessions) {
        this.service = service;
        this.identityStore = identityStore;
        this.entityMapper = entityMapper;
        this.sessions = sessions;
    }

    public OnlineUsersResource() {
        this(null, null, null, null);
    }

    @Override
    @RolesAllowed({"Administrator", "User"})
    public Table<OnlineUserForm> list(OnlineUsersSearch search) {
        long count = service.count(search);
        List<User> users = service.find(search);
        List<OnlineUserForm> resources = new ArrayList<>();

        for (User user : users) {
            boolean online = sessions.getPool().containsKey(user.getId().toString());
            OnlineUserForm onlineUser = entityMapper.map(user, OnlineUserForm.class);
            onlineUser.setOnline(online);
            resources.add(onlineUser);
        }

        Table<OnlineUserForm> table = new Table<>(resources, count) {};

        table.visible("firstName", "lastName", "online", "picture");

        return table;
    }

}
