package com.socio.socio.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCreatedEvent {

    private Long postId;
    private Long userId;
    private String content;

    public PostCreatedEvent(Long postId, Long userId, String content) {
        this.postId = postId;
        this.userId = userId;
        this.content = content;
    }

}
