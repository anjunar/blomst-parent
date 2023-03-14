package com.anjunar.blomst.shared.likes.likeable;

import com.anjunar.blomst.shared.Likeable;
import com.anjunar.common.rest.mapper.annotations.MapperConverterType;
import com.anjunar.common.security.IdentityStore;
import com.anjunar.common.security.User;
import jakarta.enterprise.inject.spi.CDI;

import java.util.Set;

public class LikesConverter implements MapperConverterType<Set<User>, LikesType, Likeable> {

    @Override
    public LikesType factory(Set<User> entity) {
        IdentityStore identityStore = CDI.current().select(IdentityStore.class).get();
        User user = identityStore.getUser();

        int size = entity.size();

        LikesType likesType = new LikesType();
        likesType.setCount(size);
        likesType.setActive(entity.contains(user));

        return likesType;
    }

    @Override
    public Set<User> updater(LikesType dto, Likeable likeable) {
        IdentityStore identityStore = CDI.current().select(IdentityStore.class).get();
        User user = identityStore.getUser();
        if (dto.isActive()) {
            likeable.getLikes().add(user);
        } else {
            likeable.getLikes().remove(user);
        }
        return likeable.getLikes();
    }
}
