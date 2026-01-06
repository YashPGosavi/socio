package com.socio.socio.service;

import com.socio.socio.event.PostLikedEvent;
import com.socio.socio.kafka.PostEventProducer;
import com.socio.socio.model.Like;
import com.socio.socio.model.Post;
import com.socio.socio.model.User;
import com.socio.socio.repository.LikeRepository;
import com.socio.socio.repository.PostRepository;
import com.socio.socio.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostEventProducer producer;

    public LikeService(
            LikeRepository likeRepository,
            UserRepository userRepository,
            PostRepository postRepository, PostEventProducer producer) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.producer = producer;
    }

    public void likePost(Long userId, Long postId) {
        if (likeRepository.findByUserIdAndPostId(userId, postId).isPresent()) {
            throw new IllegalArgumentException("Post already liked by user");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);

        likeRepository.save(like);

        producer.sendPostLikedEvent(
                new PostLikedEvent(postId, userId)
        );
    }

    public  void unlikePost(Long userId, Long postId) {
        Like like = likeRepository.findByUserIdAndPostId(userId, postId)
                .orElseThrow(() -> new IllegalArgumentException("Like not found"));

        likeRepository.delete(like);
    }

    public long getLikeCount(Long postId) {
        return likeRepository.countByPostId(postId);
    }
}
