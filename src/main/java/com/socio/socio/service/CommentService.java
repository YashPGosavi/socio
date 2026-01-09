package com.socio.socio.service;

import com.socio.socio.event.PostCommentedEvent;
import com.socio.socio.kafka.PostEventProducer;
import com.socio.socio.model.Comment;
import com.socio.socio.model.Post;
import com.socio.socio.model.User;
import com.socio.socio.repository.CommentRepository;
import com.socio.socio.repository.PostRepository;
import com.socio.socio.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostEventProducer producer;

    public CommentService(CommentRepository commentRepository,
                          UserRepository userRepository,
                          PostRepository postRepository, PostEventProducer producer) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.producer = producer;
    }

    public Comment addComment(Long userId, Long postId, String content) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setPost(post);

        Comment savedComment =  commentRepository.save(comment);

        producer.sendPostCommentedEvent(
                new PostCommentedEvent(postId, userId, content)
        );

        return savedComment;
    }

    public void deleteComment(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized to delete this comment");
        }
        commentRepository.delete(comment);
    }

    public List<Comment> getCommentsByPost(Long postId) {
        return commentRepository.findByPostId(postId);
    }
}
