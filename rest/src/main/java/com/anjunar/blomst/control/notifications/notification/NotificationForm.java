package com.anjunar.blomst.control.notifications.notification;

import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.AbstractRestEntityConverter;
import com.anjunar.common.rest.api.ImageType;
import com.anjunar.common.rest.api.json.annotations.JsonSchema;
import com.anjunar.common.rest.api.json.schema.JsonNode;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.control.notifications.Notification;

import jakarta.persistence.EntityManager;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class NotificationForm extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.TEXTAREA, title = "Text", readOnly = true)
    private String text;

    @JsonSchema(widget = JsonNode.Widget.TEXTAREA, title = "Description", readOnly = true)
    private String description;

    @JsonSchema(widget = JsonNode.Widget.IMAGE, title = "Image", readOnly = true)
    private ImageType picture;

    @JsonSchema(widget = JsonNode.Widget.CHECKBOX, title = "Acknowledged")
    private boolean acknowledged;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImageType getPicture() {
        return picture;
    }

    public void setPicture(ImageType picture) {
        this.picture = picture;
    }

    public boolean isAcknowledged() {
        return acknowledged;
    }

    public void setAcknowledged(boolean acknowledged) {
        this.acknowledged = acknowledged;
    }

    public static class NotificationFormConverter extends AbstractRestEntityConverter<Notification, NotificationForm> {

        public static final NotificationFormConverter INSTANCE = new NotificationFormConverter();

        @Override
        public Notification updater(NotificationForm restEntity, Notification entity, EntityManager entityManager, IdentityProvider identityProvider) {
            entity.setAcknowledge(restEntity.isAcknowledged());
            return entity;
        }

        @Override
        public NotificationForm factory(NotificationForm restEntity, Notification entity) {
            NotificationForm form = super.factory(restEntity, entity);
            form.setText(entity.getText());
            form.setDescription(entity.getDescription());
            form.setPicture(ImageType.factory(entity.getSource().getPicture()));
            form.setAcknowledged(entity.isAcknowledge());
            return form;
        }
    }

    public static NotificationForm factory(Notification entity) {
        return NotificationForm.NotificationFormConverter.INSTANCE.factory(new NotificationForm(), entity);
    }

    public static Notification updater(NotificationForm resource, Notification entity, EntityManager entityManager, IdentityProvider identityProvider) {
        return NotificationForm.NotificationFormConverter.INSTANCE.updater(resource, entity, entityManager, identityProvider);
    }


}
