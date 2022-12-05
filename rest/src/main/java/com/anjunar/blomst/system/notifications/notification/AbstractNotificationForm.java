package com.anjunar.blomst.system.notifications.notification;

import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ConnectionNotificationForm.class, name = "connection")
})
@JsonSchema(widget = JsonNode.Widget.FORM)
public abstract class AbstractNotificationForm extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.CHECKBOX, title = "Acknowledge")
    private boolean acknowledge;

    public boolean isAcknowledge() {
        return acknowledge;
    }

    public void setAcknowledge(boolean acknowledge) {
        this.acknowledge = acknowledge;
    }

}
