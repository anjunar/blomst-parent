package com.anjunar.blomst.system.notifications.notification;

import com.anjunar.blomst.control.notifications.ConnectionNotification;
import com.anjunar.blomst.shared.users.user.UserReference;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.common.rest.mapper.annotations.MapperPolymorphism;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@MapperPolymorphism(ConnectionNotification.class)
@JsonTypeName("ConnectionNotification")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true)
public class ConnectionNotificationForm extends AbstractNotificationForm {

    @JsonSchema(widget = JsonNode.Widget.REFERENCE, title = "To", readOnly = true)
    @JsonProperty(required = true)
    private UserReference to;

    public UserReference getTo() {
        return to;
    }

    public void setTo(UserReference to) {
        this.to = to;
    }

}
