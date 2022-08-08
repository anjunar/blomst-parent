package com.anjunar.blomst.social.timeline.post.comments.comment;

import com.anjunar.blomst.social.timeline.Comment;
import com.anjunar.common.security.IdentityProvider;

import jakarta.persistence.EntityManager;
import java.util.UUID;

public class OwnerCommentIdentity {

    private final IdentityProvider identityProvider;

    private final EntityManager entityManager;

    public OwnerCommentIdentity(IdentityProvider identityProvider, EntityManager entityManager) {
        this.identityProvider = identityProvider;
        this.entityManager = entityManager;
    }

    public boolean apply(UUID id) {
        Comment comment = entityManager.find(Comment.class, id);

        if (identityProvider.hasRole("Administrator")) {
            return true;
        }

        return comment.getOwner().equals(identityProvider.getUser());
    }
}
