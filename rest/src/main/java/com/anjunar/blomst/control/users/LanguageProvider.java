package com.anjunar.blomst.control.users;

import com.anjunar.common.i18n.Language;
import com.anjunar.common.i18n.Language_;
import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.User;
import com.anjunar.common.security.User_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.Objects;
import java.util.UUID;

public class LanguageProvider extends AbstractRestPredicateProvider<UUID, User> {
    @Override
    public Predicate build(UUID value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<User> root, CriteriaQuery<?> query) {
        if (Objects.nonNull(value)) {
            return builder.equal(root.get(User_.language).get(Language_.id), value);
        }
        return builder.conjunction();
    }
}
