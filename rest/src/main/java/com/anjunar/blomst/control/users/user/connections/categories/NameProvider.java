package com.anjunar.blomst.control.users.user.connections.categories;

import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.Category_;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.Category;
import com.anjunar.introspector.bean.BeanProperty;
import org.apache.commons.lang3.StringUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class NameProvider extends AbstractRestPredicateProvider<String, Category> {
    @Override
    public Predicate build(String value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<Category> root, CriteriaQuery<?> query, BeanProperty<?, ?> property) {
        if (StringUtils.isAllEmpty(value)) {
            return builder.conjunction();
        }
        return builder.like(builder.lower(builder.function("jsonPathAsText", String.class, root.get(Category_.i18nName), builder.literal(identityManager.getLanguage()))), value.toLowerCase() + "%");
    }
}
