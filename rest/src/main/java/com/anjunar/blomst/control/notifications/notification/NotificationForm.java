package com.anjunar.blomst.control.notifications.notification;

import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.ImageType;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;

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


}
