package com.socio.socio.controller;

import com.socio.socio.service.LikeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/{postId}")
    public String likePost(@RequestAttribute Long userId,
                           @PathVariable Long postId) {
        likeService.likePost(userId, postId);
        return "Post Liked";
    }

    @DeleteMapping("/{postId}")
    public String unlikePost(@RequestAttribute Long userId,
                             @PathVariable Long postId) {
        likeService.unlikePost(userId, postId);
        return "Post Unliked";
    }

    @GetMapping("/{postId}/count")
    public long getLikeCount(@PathVariable Long postId) {
        return likeService.getLikeCount(postId);
    }
}
