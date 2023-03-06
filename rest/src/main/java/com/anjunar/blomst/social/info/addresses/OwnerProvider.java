package com.anjunar.blomst.social.info.addresses;

import com.anjunar.blomst.control.users.Address;
import com.anjunar.blomst.control.users.AddressRight;
import com.anjunar.blomst.control.users.AddressRight_;
import com.anjunar.blomst.control.users.Address_;
import com.anjunar.common.rest.search.AbstractVisibilityPredicateProvider;
import com.anjunar.common.security.Category;
import com.anjunar.common.security.Category_;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.User_;
import com.anjunar.introspector.bean.BeanProperty;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

import java.util.Objects;
import java.util.UUID;

public class OwnerProvider extends AbstractVisibilityPredicateProvider<UUID, Address> {
    @Override
    public Predicate build(UUID value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<Address> root, CriteriaQuery<?> query, BeanProperty<?, ?> property) {
        if (Objects.nonNull(value)) {
            Join<Address, AddressRight> rightsJoin = root.join(Address_.right, JoinType.LEFT);
            SetJoin<AddressRight, Category> categoryJoin = rightsJoin.join(AddressRight_.categories, JoinType.LEFT);

            return builder.and(
                    builder.equal(root.get(Address_.owner).get(User_.id), value),
                    builder.or(
                            builder.equal(root.get(Address_.owner).get(User_.id), identityManager.getUser().getId()),
                            categoryJoin.get(Category_.id).in(generateUserConnectionCategory(builder, root, query)),
                            categoryJoin.get(Category_.id).in(generateEveryBodyCategory(builder, query))
                    )
            );
        }

        return builder.conjunction();
    }
}
