package com.anjunar.blomst.control.notifications.notification;

import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.schemamapper.NewInstanceProvider;
import com.anjunar.common.rest.schemamapper.ResourceMapper;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.control.notifications.Notification;
import com.anjunar.blomst.control.users.user.UserResource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import java.util.UUID;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.*;

@ApplicationScoped
@Path("control/notifications/notification")
public class NotificationResource implements FormResourceTemplate<NotificationForm> {

    private final EntityManager entityManager;

    private final IdentityProvider provider;

    @Inject
    public NotificationResource(EntityManager entityManager, IdentityProvider provider) {
        this.entityManager = entityManager;
        this.provider = provider;
    }

    public NotificationResource() {
        this(null, null);
    }

    @Override
    @LinkDescription("Read Notification")
    public NotificationForm read(UUID id) {
        Notification entity = entityManager.find(Notification.class, id);

        ResourceMapper mapper = new ResourceMapper();
        NotificationForm form = mapper.map(entity, NotificationForm.class);

        linkTo(methodOn(NotificationResource.class).update(id, new NotificationForm()))
                .build(form::addLink);

        linkTo(methodOn(NotificationResource.class).delete(id))
                .build(form::addLink);

        linkTo(methodOn(UserResource.class).read(entity.getSource().getId()))
                .withRel("user")
                .build(form::addLink);

        return form;
    }

    @Override
    @Transactional
    @LinkDescription("Save Notification")
    public NotificationForm save(NotificationForm form) {

        NewInstanceProvider instanceProvider = (uuid, sourceClass) -> entityManager.find(sourceClass, uuid);
        ResourceMapper mapper = new ResourceMapper(instanceProvider);
        Notification entity = mapper.map(form, Notification.class);

        entityManager.persist(entity);

        linkTo(methodOn(NotificationResource.class).update(form.getId(), new NotificationForm()))
                .build(form::addLink);

        linkTo(methodOn(NotificationResource.class).delete(form.getId()))
                .build(form::addLink);

        return form;
    }

    @Override
    @Transactional
    @LinkDescription("Update Notification")
    public NotificationForm update(UUID id, NotificationForm form) {

        NewInstanceProvider instanceProvider = (uuid, sourceClass) -> entityManager.find(sourceClass, uuid);
        ResourceMapper mapper = new ResourceMapper(instanceProvider);
        mapper.map(form, Notification.class);

        linkTo(methodOn(NotificationResource.class).update(id, new NotificationForm()))
                .build(form::addLink);

        linkTo(methodOn(NotificationResource.class).delete(id))
                .build(form::addLink);

        return form;
    }

    @Override
    @Transactional
    @LinkDescription("Delete Notification")
    public ResponseOk delete(UUID id) {

        Notification entity = entityManager.getReference(Notification.class, id);

        entityManager.remove(entity);

        return new ResponseOk();
    }
}
