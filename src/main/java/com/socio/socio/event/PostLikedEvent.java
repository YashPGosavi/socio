package com.socio.socio.event;

import lombok.Getter;

@Getter
public class PostLikedEvent {

    private Long postId;
    private Long likedByUserId;

    public PostLikedEvent(Long postId, Long likedByUserId) {
        this.postId = postId;
        this.likedByUserId = likedByUserId;
    }
}
