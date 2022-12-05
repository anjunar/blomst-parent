package com.anjunar.blomst.system.notifications;

import com.anjunar.blomst.control.notifications.AbstractNotification;
import com.anjunar.blomst.system.notifications.notification.AbstractNotificationForm;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.security.IdentityStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Path("system/notifications")
public class NotificationsResource implements ListResourceTemplate<AbstractNotificationForm, NotificationsSearch> {

    private final NotificationsService service;

    private final ResourceEntityMapper entityMapper;

    private final IdentityStore identityStore;

    @Inject
    public NotificationsResource(NotificationsService service, ResourceEntityMapper entityMapper, IdentityStore identityStore) {
        this.service = service;
        this.entityMapper = entityMapper;
        this.identityStore = identityStore;
    }

    public NotificationsResource() {
        this(null, null, null);
    }

    @Override
    public Table<AbstractNotificationForm> list(NotificationsSearch search) {
        search.setOwner(identityStore.getUser().getId());
        List<AbstractNotification> notifications = service.find(search);
        long count = service.count(search);
        List<AbstractNotificationForm> forms = new ArrayList<>();
        for (AbstractNotification notification : notifications) {
            AbstractNotificationForm abstractNotificationForm = entityMapper.map(notification, AbstractNotificationForm.class);
            forms.add(abstractNotificationForm);
        }
        return new Table<>(forms, count) {};
    }
}
