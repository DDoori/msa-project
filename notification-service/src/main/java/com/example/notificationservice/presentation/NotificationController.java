package com.example.notificationservice.presentation;

import com.example.notificationservice.application.NotificationService;
import com.example.notificationservice.dto.NotificationRequest;
import com.example.notificationservice.dto.NotificationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationResponse> create(@Valid @RequestBody NotificationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(NotificationResponse.from(notificationService.create(request)));
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> findByUserId(
            @RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(notificationService.findByUserId(UUID.fromString(userId))
                .stream()
                .map(NotificationResponse::from)
                .toList());
    }
}
