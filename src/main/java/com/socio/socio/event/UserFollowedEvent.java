package com.socio.socio.event;

import lombok.Getter;

@Getter
public class UserFollowedEvent {

    private Long followerId;
    private Long followingId;

    public UserFollowedEvent(Long followerId, Long followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
    }

}
