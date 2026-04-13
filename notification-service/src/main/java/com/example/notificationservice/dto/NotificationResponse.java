package com.example.notificationservice.dto;


import com.example.notificationservice.domain.Notification;
import com.example.notificationservice.domain.NotificationType;
import lombok.Getter;

import java.util.UUID;

@Getter
public class NotificationResponse {

    private UUID id;
    private UUID userId;
    private UUID orderId;
    private NotificationType type;
    private String message;

    private NotificationResponse(Notification notification) {
        this.id = notification.getId();
        this.userId = notification.getUserId();
        this.orderId = notification.getOrderId();
        this.type = notification.getType();
        this.message = notification.getMessage();
    }

    public static NotificationResponse from(Notification notification) {
        return new NotificationResponse(notification);
    }
}