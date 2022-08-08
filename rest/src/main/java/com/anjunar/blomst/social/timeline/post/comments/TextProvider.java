package com.anjunar.blomst.social.timeline.post.comments;

import com.anjunar.blomst.social.timeline.Comment;
import com.anjunar.blomst.social.timeline.Comment_;
import com.anjunar.common.rest.api.jaxrs.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityProvider;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class TextProvider extends AbstractRestPredicateProvider<String, Comment> {
    @Override
    public Predicate build(String value, IdentityProvider identityProvider, EntityManager entityManager, CriteriaBuilder builder, Root<Comment> root, CriteriaQuery<?> query) {
        if (value == null || value.equals("")) {
            return builder.conjunction();
        }
        return builder.gt(builder.function("contains", Integer.class, root.get(Comment_.text), builder.literal("about(" + value + ")")), 0);
    }
}
