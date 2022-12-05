package com.anjunar.blomst.system.notifications;

import com.anjunar.blomst.control.notifications.AbstractNotification;
import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.IdentityManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class NotificationsService extends AbstractCriteriaSearchService<AbstractNotification, NotificationsSearch> {

    @Inject
    public NotificationsService(EntityManager entityManager, IdentityManager identityManager) {
        super(entityManager, identityManager);
    }

    public NotificationsService() {
        this(null, null);
    }

}
