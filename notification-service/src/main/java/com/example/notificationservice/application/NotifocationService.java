package com.example.notificationservice.application;

import com.example.notificationservice.domain.Notification;
import com.example.notificationservice.dto.NotificationRequest;
import com.example.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final SlackNotificationService slackNotificationService;

    public Notification create(NotificationRequest request) {
        Notification notification = Notification.builder()
                .userId(request.getUserId())
                .orderId(request.getOrderId())
                .type(request.getType())
                .message(request.getMessage())
                .build();

        notificationRepository.save(notification);

        slackNotificationService.send(
                "[" + request.getType() + "] " + request.getMessage() +
                        "\n주문 ID: " + request.getOrderId() +
                        "\n사용자 ID: " + request.getUserId()
        );

        return notification;
    }

    @Transactional(readOnly = true)
    public List<Notification> findByUserId(UUID userId) {
        return notificationRepository.findByUserId(userId);
    }
}
