package com.socio.socio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponce {

    private Long postId;
    private String content;
    private Long userId;
    private String userName;

}
