package com.anjunar.blomst.social.timeline.post;

import com.anjunar.blomst.social.timeline.AbstractPost;
import com.anjunar.common.rest.MethodPredicateHandler;
import com.anjunar.common.security.IdentityManager;

import jakarta.persistence.EntityManager;
import java.util.UUID;

public class OwnerPostIdentity implements MethodPredicateHandler {

    private final IdentityManager identityManager;

    private final EntityManager entityManager;

    public OwnerPostIdentity(IdentityManager identityManager, EntityManager entityManager) {
        this.identityManager = identityManager;
        this.entityManager = entityManager;
    }

    public boolean apply(UUID id) {
        AbstractPost post = entityManager.find(AbstractPost.class, id);
        if (identityManager.hasRole("Administrator")) {
            return true;
        }
        return post.getOwner().equals(identityManager.getUser());
    }
}
