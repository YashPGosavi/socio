package com.socio.socio.dto;

import lombok.Getter;

@Getter
public class FeedPostResponse {

    private Long postId;
    private String content;
    private Long userId;
    private String username;

    public FeedPostResponse(Long postId, String content, Long userId, String username) {
        this.postId = postId;
        this.content = content;
        this.userId = userId;
        this.username = username;
    }
}
