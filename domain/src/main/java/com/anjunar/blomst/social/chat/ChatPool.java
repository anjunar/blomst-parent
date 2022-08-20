package com.anjunar.blomst.social.chat;

import com.anjunar.common.security.User;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class ChatPool {

    private final Map<User,ChatClient> pool = new ConcurrentHashMap<>();

    public void add(User user, ChatClient client) {
        pool.put(user, client);
    }

    public ChatClient find(User user){
        return pool.get(user);
    }

}
