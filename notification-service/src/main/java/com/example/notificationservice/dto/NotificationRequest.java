package com.example.notificationservice.dto;


import com.example.notificationservice.domain.NotificationType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class NotificationRequest {

    @NotNull
    private UUID userId;

    @NotNull
    private UUID orderId;

    @NotNull
    private NotificationType type;

    @NotNull
    private String message;
}