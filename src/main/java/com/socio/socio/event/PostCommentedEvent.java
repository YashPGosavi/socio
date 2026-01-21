package com.socio.socio.event;

import lombok.Getter;

@Getter
public class PostCommentedEvent {

    private Long postId;
    private Long commentedByUserId;
    private String content;

    public PostCommentedEvent(Long postId, Long commentedByUserId, String content) {
        this.postId = postId;
        this.commentedByUserId = commentedByUserId;
        this.content = content;
    }

}
