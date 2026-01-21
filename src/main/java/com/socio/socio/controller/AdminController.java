package com.socio.socio.controller;

import com.socio.socio.dto.PostStatsResponse;
import com.socio.socio.dto.UserStatsResponse;
import com.socio.socio.repository.CommentRepository;
import com.socio.socio.repository.FollowRepository;
import com.socio.socio.repository.LikeRepository;
import com.socio.socio.util.CsvUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final FollowRepository followRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    public AdminController(FollowRepository followRepository, LikeRepository likeRepository, CommentRepository commentRepository) {
        this.followRepository = followRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping("/health")
    public String adminHealth() {
        return "Admin API working";
    }

    @GetMapping("/stats/users")
    public List<UserStatsResponse> usersByFollowers() {
        return followRepository.findUsersOrderedByFollowers();
    }

    @GetMapping("/stats/posts/likes")
    public List<PostStatsResponse> postsByLikes() {
        return likeRepository.findPostsOrderedByLikes();
    }

    @GetMapping("/stats/posts/comments")
    public List<PostStatsResponse> postsByComments() {
        return commentRepository.findPostsOrderedByComments();
    }

    @GetMapping(value = "/export/users", produces = "text/csv")
    public String exportUsersCsv() {
        return CsvUtil.usersToCsv(
                followRepository.findUsersOrderedByFollowers()
        );
    }

}
