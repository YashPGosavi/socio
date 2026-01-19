package com.socio.socio.controller;

import com.socio.socio.model.Follow;
import com.socio.socio.repository.FollowRepository;
import com.socio.socio.service.FollowService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follow")
public class FollowController {
    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/{userIdToFollow}")
    public String follow(@RequestAttribute Long userId,
                         @PathVariable Long userIdToFollow) {
        followService.followUser(userId, userIdToFollow);
        return "User followed";
    }

    @DeleteMapping("/{userIdToUnfollow}")
    public String unfollow(@RequestAttribute Long userId,
                           @PathVariable Long userIdToUnfollow) {
        followService.unfollowUser(userId, userIdToUnfollow);
        return "User unfollowed";
    }

    @GetMapping("/{userId}/followers")
    public long getFollowersCount(@PathVariable Long userId) {
        return followService.getFollowersCount(userId);
    }

    @GetMapping("/{userId}/following")
    public long getFollowingCount(@PathVariable Long userId) {
        return followService.getFollowingCount(userId);
    }

}
