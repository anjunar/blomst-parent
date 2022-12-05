package com.anjunar.blomst.system.notifications.notification;

import com.anjunar.blomst.WebSocketSessions;
import com.anjunar.blomst.control.notifications.ConnectionNotification;
import com.anjunar.common.websocket.WebSocketProtocol;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.websocket.Session;

import java.util.Objects;

@ApplicationScoped
public class NotificationService {

    public void onConnectionNotification(@Observes ConnectionNotification notification,
                                         WebSocketSessions sessions,
                                         WebSocketProtocol webSocketProtocol) {

        Session session = sessions.getPool().get(notification.getOwner().getId().toString());

        if (Objects.nonNull(session)) {
            webSocketProtocol.protocol("notification-new", session);
        }
    }

}
