package com.anjunar.blomst.social.chat.messages;

import com.anjunar.blomst.social.chat.ChatMessage;
import com.anjunar.blomst.social.chat.ChatMessage_;
import com.anjunar.common.rest.search.AbstractRestPredicateProvider;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.User;
import com.anjunar.common.security.User_;
import com.anjunar.introspector.bean.BeanProperty;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

import java.util.Set;
import java.util.UUID;

public class ParticipantsProvider extends AbstractRestPredicateProvider<Set<UUID>, ChatMessage> {
    @Override
    public Predicate build(Set<UUID> value, IdentityManager identityManager, EntityManager entityManager, CriteriaBuilder builder, Root<ChatMessage> root, CriteriaQuery<?> query, BeanProperty<?, ?> property) {
        if (value == null || value.isEmpty()) {
            return builder.conjunction();
        }
        Subquery<UUID> subQuery = query.subquery(UUID.class);
        Root<ChatMessage> subFrom = subQuery.from(ChatMessage.class);
        SetJoin<ChatMessage, User> subJoin = subFrom.join(ChatMessage_.participants);

        subQuery.select(subFrom.get(ChatMessage_.id))
                .where(subJoin.get(User_.id).in(value))
                .groupBy(subFrom.get(User_.id))
                .having(builder.equal(builder.count(subFrom), value.size()));

        return root.get(ChatMessage_.id).in(subQuery);
    }
}
