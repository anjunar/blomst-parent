package com.anjunar.blomst.system.notifications.notification;

import com.anjunar.blomst.control.notifications.AbstractNotification;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.security.IdentityStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;

import java.util.UUID;

@ApplicationScoped
@Path("system/notifications/notification")
public class NotificationResource {

    private final EntityManager entityManager;

    private final IdentityStore identityStore;

    @Inject
    public NotificationResource(EntityManager entityManager, IdentityStore identityStore) {
        this.entityManager = entityManager;
        this.identityStore = identityStore;
    }

    public NotificationResource() {
        this(null, null);
    }

    @GET
    @Path("active")
    @Produces("application/json")
    public NotificationActiveResponse activeNotifications() {
        Long count = entityManager.createQuery("select count(a) from AbstractNotification a where a.acknowledge = false and a.owner = :owner", Long.class)
                .setParameter("owner", identityStore.getUser())
                .getSingleResult();
        NotificationActiveResponse response = new NotificationActiveResponse();
        response.setSize(count);
        return response;
    }

    @POST
    @Path("active")
    @Produces("application/json")
    public ResponseOk deactivateNotification(@QueryParam("id") UUID id) {
        AbstractNotification notification = entityManager.find(AbstractNotification.class, id);
        notification.setAcknowledge(true);
        return new ResponseOk();
    }

}
