package com.socio.socio.dto;

import lombok.Getter;

@Getter
public class PostResponce {

    private Long postId;
    private String content;
    private Long userId;
    private String userName;

    public PostResponce(Long postId, String content, Long userId, String userName) {
        this.postId = postId;
        this.content = content;
        this.userId = userId;
        this.userName = userName;
    }

}
