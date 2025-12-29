package com.socio.socio.service;

import com.socio.socio.dto.PostResponce;
import com.socio.socio.model.Post;
import com.socio.socio.model.User;
import com.socio.socio.repository.PostRepository;
import com.socio.socio.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post createPost(Long userId, String content) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Post post = new Post();
        post.setContent(content);
        post.setUser(user);

        return postRepository.save(post);
    }

    public List<PostResponce> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(post -> new PostResponce(
                        post.getId(),
                        post.getContent(),
                        post.getUser().getId(),
                        post.getUser().getName()
                ))
                .toList();
    }
}
