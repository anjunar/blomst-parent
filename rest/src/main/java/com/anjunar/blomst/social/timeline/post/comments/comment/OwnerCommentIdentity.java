package com.anjunar.blomst.social.timeline.post.comments.comment;

import com.anjunar.blomst.social.timeline.Comment;
import com.anjunar.common.security.IdentityManager;

import jakarta.persistence.EntityManager;
import java.util.UUID;

public class OwnerCommentIdentity {

    private final IdentityManager identityManager;

    private final EntityManager entityManager;

    public OwnerCommentIdentity(IdentityManager identityManager, EntityManager entityManager) {
        this.identityManager = identityManager;
        this.entityManager = entityManager;
    }

    public boolean apply(UUID id) {
        Comment comment = entityManager.find(Comment.class, id);

        if (identityManager.hasRole("Administrator")) {
            return true;
        }

        return comment.getOwner().equals(identityManager.getUser());
    }
}
