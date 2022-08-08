package com.anjunar.blomst.shared.likeable;

import com.anjunar.blomst.shared.Likeable;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.common.rest.api.AbstractRestEntityConverter;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.User;

import jakarta.persistence.EntityManager;

public class AbstractLikeableRestEntityConverter<E extends Likeable, R extends AbstractLikeableRestEntity> extends AbstractRestEntityConverter<E, R> {

    @Override
    public R factory(R restEntity, E entity) {
        restEntity.setViews(entity.getViews());
        for (User user : entity.getLikes()) {
            restEntity.getLikes().add(UserSelect.factory(user));
        }
        return super.factory(restEntity, entity);
    }

    @Override
    public E updater(R restEntity, E entity, EntityManager entityManager, IdentityProvider identityProvider) {
        entity.setViews(restEntity.getViews());
        entity.getLikes().clear();
        for (UserSelect like : restEntity.getLikes()) {
            entity.getLikes().add(entityManager.find(User.class, like.getId()));
        }
        return entity;
    }
}
