package com.socio.socio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class UserStatsResponse {

    private Long userId;
    private String name;
    private long followersCount;
}
