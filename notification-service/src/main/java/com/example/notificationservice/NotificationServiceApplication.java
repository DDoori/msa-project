package com.example.notificationservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationServiceApplication {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .directory("../")
                .ignoreIfMissing()
                .load();
        System.setProperty("SLACK_WEBHOOK_URL", dotenv.get("SLACK_WEBHOOK_URL", ""));
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
}
