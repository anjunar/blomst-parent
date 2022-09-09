package com.anjunar.blomst.system.mail;

import com.google.common.base.Strings;
import com.anjunar.common.mail.Template_;
import com.anjunar.common.mail.Template;
import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityManager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class NameProvider extends AbstractRestPredicateProvider<String, Template> {
    @Override
    public Predicate build(String value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<Template> root, CriteriaQuery<?> query) {
        if (Strings.isNullOrEmpty(value)) {
            return builder.conjunction();
        }
        return builder.like(builder.lower(root.get(Template_.name)), value.toLowerCase() + "%");
    }
}
