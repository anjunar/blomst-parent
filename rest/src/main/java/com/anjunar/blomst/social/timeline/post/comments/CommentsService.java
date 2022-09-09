package com.anjunar.blomst.social.timeline.post.comments;

import com.anjunar.blomst.social.timeline.Comment;
import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.IdentityManager;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class CommentsService extends AbstractCriteriaSearchService<Comment, CommentsSearch> {

    @Inject
    public CommentsService(EntityManager entityManager, IdentityManager identityManager) {
        super(entityManager, identityManager);
    }

    public CommentsService() {
        this(null, null);
    }

}
