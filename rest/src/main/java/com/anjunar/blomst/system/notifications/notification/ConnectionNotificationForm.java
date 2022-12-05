package com.anjunar.blomst.system.notifications.notification;

import com.anjunar.blomst.control.notifications.ConnectionNotification;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.common.rest.mapper.annotations.MapperPolymorphism;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;

@MapperPolymorphism(ConnectionNotification.class)
public class ConnectionNotificationForm extends AbstractNotificationForm {

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "To", readOnly = true)
    private UserSelect to;

    public UserSelect getTo() {
        return to;
    }

    public void setTo(UserSelect to) {
        this.to = to;
    }

}
