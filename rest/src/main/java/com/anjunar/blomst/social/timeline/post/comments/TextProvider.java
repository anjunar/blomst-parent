package com.anjunar.blomst.social.timeline.post.comments;

import com.anjunar.blomst.social.timeline.Comment;
import com.anjunar.blomst.social.timeline.Comment_;
import com.anjunar.common.i18n.Detector;
import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.introspector.bean.BeanProperty;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class TextProvider extends AbstractRestPredicateProvider<String, Comment> {

    @Override
    public Predicate build(String value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<Comment> root, CriteriaQuery<?> query, BeanProperty<?, ?> property) {
        if (value == null || value.equals("")) {
            return builder.conjunction();
        }

        String language = Detector.detectLanguageOf(value);

        return builder.equal(builder.function("distance", Boolean.class, root.get(Comment_.text), builder.literal(language), builder.literal(value)), true);
    }
}
