package com.socio.socio.service;

import com.socio.socio.dto.FeedPostResponse;
import com.socio.socio.repository.FollowRepository;
import com.socio.socio.repository.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedService {

    private final FollowRepository followRepository;
    private final PostRepository postRepository;

    public FeedService(FollowRepository followRepository, PostRepository postRepository) {
        this.followRepository = followRepository;
        this.postRepository = postRepository;
    }

    public List<FeedPostResponse> getFeed(Long userId, int page, int size) {

        List<Long> followingIds = followRepository.findFollowingIds(userId);
        if (followingIds.isEmpty()) {
            return List.of();
        }

        return postRepository.findByUserIdInOrderByIdDesc(
                followingIds,
                PageRequest.of(page,size)
        )
                .stream()
                .map(post -> new FeedPostResponse(
                        post.getId(),
                        post.getContent(),
                        post.getUser().getId(),
                        post.getUser().getName()
                )).collect(Collectors.toList());
    }
}
