package com.anjunar.blomst.social.timeline.post;

import com.anjunar.blomst.social.timeline.AbstractPost;
import com.anjunar.common.security.IdentityProvider;

import javax.persistence.EntityManager;
import java.util.UUID;

public class OwnerPostIdentity {

    private final IdentityProvider identityProvider;

    private final EntityManager entityManager;

    public OwnerPostIdentity(IdentityProvider identityProvider, EntityManager entityManager) {
        this.identityProvider = identityProvider;
        this.entityManager = entityManager;
    }

    public boolean apply(UUID id) {
        AbstractPost post = entityManager.find(AbstractPost.class, id);
        if (identityProvider.hasRole("Administrator")) {
            return true;
        }
        return post.getOwner().equals(identityProvider.getUser());
    }
}
