package com.anjunar.blomst.control.users.user.connections.connection;

import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.User;
import com.anjunar.blomst.control.notifications.Notification;
import com.anjunar.common.security.UserConnection;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.UUID;

@ApplicationScoped
public class UserConnectionService {

    private final EntityManager entityManager;

    private final IdentityManager identityManager;

    @Inject
    public UserConnectionService(EntityManager entityManager, IdentityManager identityManager) {
        this.entityManager = entityManager;
        this.identityManager = identityManager;
    }

    public UserConnectionService() {
        this(null, null);
    }

    public void createNotification(UUID owner, UUID source) {
        User fromUser = entityManager.find(User.class, source);
        User ownerUser = entityManager.find(User.class, owner);

        Notification notification = new Notification();
        notification.setOwner(ownerUser);
        notification.setSource(fromUser);
        notification.setAcknowledge(false);

        ResourceBundle bundle = ResourceBundle.getBundle("com.anjunar.blomst.i18nMessages", identityManager.getLanguage().getLocale());
        String template = bundle.getString("com.anjunar.blomst.control.users.user.connections.connection.UserConnectionService.createNotification");
        String format = MessageFormat.format(template, fromUser.getFirstName(), fromUser.getLastName());
        notification.setText(format);

        entityManager.persist(notification);
    }

    public UserConnection accepted(UUID from, UUID to) {
        try {
            return entityManager.createQuery("select c from UserConnection c where c.from.id = :from and c.to.id = :to", UserConnection.class)
                    .setParameter("from", to)
                    .setParameter("to", from)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
