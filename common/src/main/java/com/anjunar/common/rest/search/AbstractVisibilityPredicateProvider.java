package com.anjunar.common.rest.search;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.security.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

import java.util.Locale;
import java.util.UUID;

public abstract class AbstractVisibilityPredicateProvider<V, E> extends AbstractRestPredicateProvider<V, E> {

    protected static Subquery<UUID> generateUserConnectionCategory(CriteriaBuilder builder, Root<? extends AbstractEntity> root, CriteriaQuery<?> query) {
        Subquery<UUID> firstSubQuery = query.subquery(UUID.class);
        Root<UserConnection> firstSubFrom = firstSubQuery.from(UserConnection.class);
        firstSubQuery.select(firstSubFrom.get(UserConnection_.category).get(Category_.id))
                .where(builder.equal(firstSubFrom.get(UserConnection_.from), root.get(User_.id)));
        return firstSubQuery;
    }

    protected static Subquery<UUID> generateEveryBodyCategory(CriteriaBuilder builder, CriteriaQuery<?> query) {
        Subquery<UUID> secondSubQuery = query.subquery(UUID.class);
        Root<Category> secondSubFrom = secondSubQuery.from(Category.class);
        secondSubQuery.select(secondSubFrom.get(Category_.id))
                .where(builder.equal(
                        builder.function(
                                "jsonPathAsText",
                                String.class,
                                secondSubFrom.get(Category_.i18nName),
                                builder.literal(Locale.forLanguageTag("en-DE"))
                        ),
                        "Everybody")
                );
        return secondSubQuery;
    }
}
