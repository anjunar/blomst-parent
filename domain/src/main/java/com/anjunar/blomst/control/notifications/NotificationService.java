package com.anjunar.blomst.control.notifications;

import com.anjunar.common.ddd.OnPersist;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.UserConnection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

@ApplicationScoped
public class NotificationService {

    public void onUserConnection(@Observes @OnPersist UserConnection connection,
                                 EntityManager entityManager,
                                 Event<ConnectionNotification> notificationEvent) {
        try {
            entityManager.createQuery("select u from UserConnection u where u.from = :from and u.to = :to", UserConnection.class)
                    .setParameter("from", connection.getTo())
                    .setParameter("to", connection.getFrom())
                    .getSingleResult();
        } catch (NoResultException e) {
            ConnectionNotification notification = new ConnectionNotification();
            notification.setOwner(connection.getTo());
            notification.setTo(connection.getFrom());
            notification.setAcknowledge(false);
            entityManager.persist(notification);
            notificationEvent.fire(notification);
        }

    }

}
