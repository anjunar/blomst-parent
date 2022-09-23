package com.anjunar.blomst.social.chat;

import com.anjunar.blomst.ApplicationWebSocket;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.security.User;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;

import java.util.ArrayList;
import java.util.List;

@Path("social/chat/online")
public class OnlineUsersResource implements ListResourceTemplate<OnlineUserForm, OnlineUsersSearch> {

    private final OnlineUsersService service;

    private final ResourceEntityMapper entityMapper;

    @Inject
    public OnlineUsersResource(OnlineUsersService service, ResourceEntityMapper entityMapper) {
        this.service = service;
        this.entityMapper = entityMapper;
    }

    public OnlineUsersResource() {
        this(null, null);
    }

    @Override
    public Table<OnlineUserForm> list(OnlineUsersSearch search) {
        long count = service.count(search);
        List<User> users = service.find(search);
        List<OnlineUserForm> resources = new ArrayList<>();

        for (User user : users) {
            if (ApplicationWebSocket.pool.containsKey(user.getId().toString())) {
                OnlineUserForm onlineUser = entityMapper.map(user, OnlineUserForm.class);

                resources.add(onlineUser);
            }
        }

        return new Table<>(resources, count) {};
    }

}
