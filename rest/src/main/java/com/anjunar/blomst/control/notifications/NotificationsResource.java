package com.anjunar.blomst.control.notifications;

import com.anjunar.blomst.control.notifications.notification.NotificationForm;
import com.anjunar.blomst.control.notifications.notification.NotificationResource;
import com.anjunar.common.rest.LinkDescription;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.security.IdentityProvider;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Path;

import java.util.ArrayList;
import java.util.List;

import static com.anjunar.common.rest.WebURLBuilderFactory.*;

@ApplicationScoped
@Path("control/notifications")
public class NotificationsResource implements ListResourceTemplate<NotificationForm, NotificationsSearch> {

    private final NotificationsService service;

    private final IdentityProvider identityProvider;

    @Inject
    public NotificationsResource(NotificationsService service, IdentityProvider identityProvider) {
        this.service = service;
        this.identityProvider = identityProvider;
    }

    public NotificationsResource() {
        this(null, null);
    }

    @Override
    @Transactional
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Table Notification")
    public Table<NotificationForm> list(NotificationsSearch search) {
        if (! identityProvider.hasRole("Administrator")) {
            search.setOwner(identityProvider.getUser().getId());
        }

        final long count = service.count(search);
        final List<Notification> notifications = service.find(search);
        final List<NotificationForm> resources = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationForm form = NotificationForm.factory(notification);

            linkTo(methodOn(NotificationResource.class).read(notification.getId()))
                    .build(form::addLink);

            resources.add(form);
        }

        return new Table<>(resources, count) {};
    }
}
