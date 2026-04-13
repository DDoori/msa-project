package com.example.notificationservice.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class SlackNotificationService {

    @Value("${slack.webhook-url}")
    private String webhookUrl;

    private final WebClient webClient = WebClient.create();

    public void send(String message) {
        webClient.post()
                .uri(webhookUrl)
                .bodyValue(Map.of("text", message))
                .retrieve()
                .bodyToMono(String.class)
                .subscribe();
    }
}
