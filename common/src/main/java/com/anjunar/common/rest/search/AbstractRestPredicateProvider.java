package com.anjunar.common.rest.search;

import com.anjunar.common.security.IdentityManager;
import org.apache.commons.lang3.StringUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

public abstract class AbstractRestPredicateProvider<V, E> {

    public abstract Predicate build(V value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<E> root, CriteriaQuery<?> query);

    protected Path cursor(Path<?> path, String pathString) {
        if (StringUtils.isEmpty(pathString)) {
            return path;
        }
        String[] paths = pathString.split("\\.");
        Path<?> cursor = path;
        for (String segment : paths) {
            cursor = cursor.get(segment);
        }
        return cursor;
    }

}
