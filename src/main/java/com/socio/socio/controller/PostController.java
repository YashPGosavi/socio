package com.socio.socio.controller;

import com.socio.socio.dto.PostResponce;
import com.socio.socio.model.Post;
import com.socio.socio.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public Post createPost(@RequestAttribute Long userId, @RequestParam String content) {
        return postService.createPost(userId, content);
    }

    @GetMapping
    public List<PostResponce> getAllPosts() {
        return postService.getAllPosts();
    }
}
