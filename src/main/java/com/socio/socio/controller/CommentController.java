package com.socio.socio.controller;

import com.socio.socio.model.Comment;
import com.socio.socio.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{postId}")
    public Comment addComment(@RequestAttribute Long userId,
                              @PathVariable Long postId,
                              @RequestParam String content) {
        return commentService.addComment(userId, postId, content);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@RequestAttribute Long userId,
                                @PathVariable Long commentId) {
        commentService.deleteComment(userId, commentId);
    }

    @GetMapping("/post/{postId}")
    public List<Comment> getCommentsByPost(@PathVariable Long postId) {
        return commentService.getCommentsByPost(postId);
    }
}
