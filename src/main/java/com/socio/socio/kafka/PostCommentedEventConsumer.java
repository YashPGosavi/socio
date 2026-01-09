package com.socio.socio.kafka;

import com.socio.socio.event.PostCommentedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class PostCommentedEventConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(
        topics = "post-commented-events",
        groupId = "notification-group"
    )
    public void consume(String message) {
        try {
            PostCommentedEvent event = objectMapper.readValue(message, PostCommentedEvent.class);
            System.out.println(
                " Post " + event.getPostId()
                + " commented by User " + event.getCommentedByUserId()
                + " : " + event.getContent()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
