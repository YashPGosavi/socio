package com.socio.socio.kafka;

import com.socio.socio.event.PostCommentedEvent;
import com.socio.socio.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class PostCommentedEventConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final NotificationService notificationService;

    public PostCommentedEventConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(
        topics = "post-commented-events",
        groupId = "notification-group"
    )
    public void consume(String message) {
        try {
            PostCommentedEvent event = objectMapper.readValue(message, PostCommentedEvent.class);

            notificationService.createNotification(
                    event.getPostId(),
                    "Your post was commented on by user " + event.getCommentedByUserId()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
