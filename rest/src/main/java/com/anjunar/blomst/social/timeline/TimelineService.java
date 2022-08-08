package com.anjunar.blomst.social.timeline;

import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.control.users.UserConnection;
import com.anjunar.blomst.social.communities.CommunitiesConnection;
import com.anjunar.blomst.social.sites.SiteConnection;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TimelineService extends AbstractCriteriaSearchService<AbstractPost, TimelineSearch> {

    @Inject
    public TimelineService(EntityManager entityManager, IdentityProvider identityProvider) {
        super(entityManager, identityProvider);
    }

    public TimelineService() {
        this(null, null);
    }

    public Set<UUID> connections() {
        List<CommunitiesConnection> communitiesConnections = entityManager.createQuery("select c from CommunitiesConnection c where c.from = :user", CommunitiesConnection.class)
                .setParameter("user", identityProvider.getUser())
                .getResultList();

        List<UserConnection> userConnections = entityManager.createQuery("select c from UserConnection c where c.from = :user", UserConnection.class)
                .setParameter("user", identityProvider.getUser())
                .getResultList();

        List<SiteConnection> siteConnections = entityManager.createQuery("select c from SiteConnection c where c.from = :user", SiteConnection.class)
                .setParameter("user", identityProvider.getUser())
                .getResultList();

        Set<UUID> result = new HashSet<>();
        for (CommunitiesConnection connection : communitiesConnections) {
            result.add(connection.getTo().getId());
        }
        for (UserConnection userConnection : userConnections) {
            result.add(userConnection.getTo().getId());
        }
        for (SiteConnection siteConnection : siteConnections) {
            result.add(siteConnection.getTo().getId());
        }

        result.add(identityProvider.getUser().getId());

        return result;
    }

}
