package com.anjunar.blomst.shared.likes;

import com.anjunar.blomst.shared.Likeable;
import com.anjunar.blomst.shared.likeable.AbstractLikeableResource;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.timeline.AbstractPost;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.security.IdentityStore;
import com.anjunar.common.security.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Path("shared/likes")
public class LikesResource extends AbstractLikeableResource implements ListResourceTemplate<UserSelect, LikesSearch> {

    private final LikesService service;

    private final EntityManager entityManager;

    private final ResourceEntityMapper entityMapper;

    @Inject
    public LikesResource(IdentityStore identityStore, LikesService service, EntityManager entityManager, ResourceEntityMapper entityMapper) {
        super(identityStore);
        this.service = service;
        this.entityManager = entityManager;
        this.entityMapper = entityMapper;
    }

    public LikesResource() {
        this(null, null, null, null);
    }

    @Override
    protected Likeable load(UUID id) {
        return entityManager.find(AbstractPost.class, id);
    }

    @Override
    public Table<UserSelect> list(LikesSearch search) {
        long count = service.count(search);
        List<User> users = service.find(search);
        List<UserSelect> selects = new ArrayList<>();

        for (User user : users) {
            UserSelect userSelect = entityMapper.map(user, UserSelect.class);
            selects.add(userSelect);
        }

        return new Table<>(selects, count) {};
    }
}
