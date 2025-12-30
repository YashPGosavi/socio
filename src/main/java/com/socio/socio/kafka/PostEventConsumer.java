package com.socio.socio.kafka;

import com.socio.socio.event.PostCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class PostEventConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "post-events", groupId = "notification-group")
    public void consume(String message) {
        try{
            PostCreatedEvent event = objectMapper.readValue(message, PostCreatedEvent.class);
            System.out.println(
                    "Notification send for Post ID: " + event.getPostId()
                    + ", User ID: " + event.getUserId()
            );
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
