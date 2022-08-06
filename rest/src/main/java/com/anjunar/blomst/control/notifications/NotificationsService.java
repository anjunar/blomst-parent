package com.anjunar.blomst.control.notifications;

import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.IdentityProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class NotificationsService extends AbstractCriteriaSearchService<Notification, NotificationsSearch> {

    @Inject
    public NotificationsService(EntityManager entityManager, IdentityProvider identityProvider) {
        super(entityManager, identityProvider);
    }

    public NotificationsService() {
        this(null, null);
    }

}
