package com.socio.socio.kafka;

import com.socio.socio.event.PostCommentedEvent;
import com.socio.socio.event.PostCreatedEvent;
import com.socio.socio.event.PostLikedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class PostEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PostEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPostCreatedEvent(PostCreatedEvent event) {
        try{
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("post-events", message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendPostLikedEvent(PostLikedEvent event) {
        try{
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("post-liked-events", message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendPostCommentedEvent(PostCommentedEvent event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("post-commented-events", message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
