package com.socio.socio.kafka;

import com.socio.socio.event.PostLikedEvent;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class PostLikedEventConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void consume(String message) {
        try {
            PostLikedEvent event = objectMapper.readValue(message, PostLikedEvent.class);

            System.out.println(
                    "Notification send for Liked Post ID: " + event.getPostId()
                    + ", Liked By User ID: " + event.getLikedByUserId()
            );
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
