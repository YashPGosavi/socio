package com.socio.socio.kafka;

import com.socio.socio.event.UserFollowedEvent;
import com.socio.socio.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class UserFollowedEventConsume {

    private final ObjectMapper mapper = new ObjectMapper();
    private final NotificationService notificationService;

    public UserFollowedEventConsume(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(
        topics = "user-followed-events",
        groupId = "notification-group"
    )
    public void consume(String message) {
        try{
            UserFollowedEvent event = mapper.readValue(message, UserFollowedEvent.class);

            notificationService.createNotification(
                    event.getFollowingId(),
                    "Someone started following you."
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
