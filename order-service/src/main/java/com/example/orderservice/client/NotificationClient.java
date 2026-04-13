package com.example.orderservice.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "notification-service")
public interface NotificationClient {

    @PostMapping("/notifications")
    void sendNotification(@RequestBody NotificationRequest request);

    @Getter
    @AllArgsConstructor
    class NotificationRequest {
        private UUID userId;
        private UUID orderId;
        private String type;
        private String message;
    }
}