package com.socio.socio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostStatsResponse {
    private Long postId;
    private String content;
    private long count;
}
