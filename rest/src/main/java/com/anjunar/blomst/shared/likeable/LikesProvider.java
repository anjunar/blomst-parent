package com.anjunar.blomst.shared.likeable;

import com.anjunar.common.security.User_;
import com.anjunar.blomst.social.pages.page.Question;
import com.anjunar.blomst.shared.Likeable_;
import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class LikesProvider extends AbstractRestPredicateProvider<Set<UUID>, Question> {
    @Override
    public Predicate build(Set<UUID> value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<Question> root, CriteriaQuery<?> query) {
        if (value != null && value.size() > 0) {
            Set<UUID> users = new HashSet<>();
            for (UUID userSelect : value) {
                User user = entityManager.find(User.class, userSelect);
                users.add(user.getId());
            }
            return root.join(Likeable_.likes).get(User_.id).in(users);
        }
        return builder.conjunction();
    }
}
