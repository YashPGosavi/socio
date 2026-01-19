package com.socio.socio.kafka;

import com.socio.socio.event.PostLikedEvent;
import com.socio.socio.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class PostLikedEventConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final NotificationService notificationService;

    public PostLikedEventConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(
        topics = "post-liked-events",
        groupId = "notification-group"
    )
    public void consume(String message) {
        try {
            PostLikedEvent event = objectMapper.readValue(message, PostLikedEvent.class);

            notificationService.createNotification(
                    event.getPostId(),
                    "Your post was liked by user " + event.getLikedByUserId()
            );
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
