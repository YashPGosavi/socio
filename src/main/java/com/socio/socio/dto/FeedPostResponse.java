package com.socio.socio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FeedPostResponse {

    private Long postId;
    private String content;
    private Long userId;
    private String username;

}
