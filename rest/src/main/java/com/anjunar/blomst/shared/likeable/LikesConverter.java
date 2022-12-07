package com.anjunar.blomst.shared.likeable;

import com.anjunar.blomst.shared.Likeable;
import com.anjunar.common.rest.mapper.annotations.MapperConverterType;
import com.anjunar.common.security.IdentityStore;
import com.anjunar.common.security.User;
import jakarta.enterprise.inject.spi.CDI;

import java.util.Set;

public class LikesConverter implements MapperConverterType<Set<User>, Boolean, Likeable> {

    @Override
    public Boolean factory(Set<User> entity) {
        IdentityStore identityStore = CDI.current().select(IdentityStore.class).get();
        User user = identityStore.getUser();
        return entity.contains(user);
    }

    @Override
    public Set<User> updater(Boolean dto, Likeable likeable) {
        IdentityStore identityStore = CDI.current().select(IdentityStore.class).get();
        User user = identityStore.getUser();
        if (dto) {
            likeable.getLikes().add(user);
        } else {
            likeable.getLikes().remove(user);
        }
        return likeable.getLikes();
    }
}
