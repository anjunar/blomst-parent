package com.anjunar.blomst.shared.likeable;

import com.anjunar.common.security.User_;
import com.anjunar.blomst.social.pages.page.Question;
import com.anjunar.blomst.shared.Likeable_;
import com.anjunar.common.rest.api.jaxrs.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.User;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class LikesProvider extends AbstractRestPredicateProvider<Set<UUID>, Question> {
    @Override
    public Predicate build(Set<UUID> value, IdentityProvider identityProvider, EntityManager entityManager, CriteriaBuilder builder, Root<Question> root, CriteriaQuery<?> query) {
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
