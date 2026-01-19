package com.socio.socio.service;

import com.socio.socio.exception.BadRequestException;
import com.socio.socio.exception.NotFoundException;
import com.socio.socio.model.Follow;
import com.socio.socio.model.User;
import com.socio.socio.repository.FollowRepository;
import com.socio.socio.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public FollowService(FollowRepository followRepository, UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    public void followUser(Long followerId, Long followingId) {
        if(followerId.equals(followingId)) {
            throw new BadRequestException("You cannot follow yourself");
        }

        if(followRepository.findByFollowerIdAndFollowingId(followerId, followingId).isPresent()) {
            throw new BadRequestException("Already following this user");
        }

        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new NotFoundException("Follower user not found"));

        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new NotFoundException("Following user not found"));

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);

        followRepository.save(follow);

    }

    public void unfollowUser(Long followerId, Long followingId) {
        Follow follow = followRepository.findByFollowerIdAndFollowingId(followerId, followingId)
                .orElseThrow(() -> new NotFoundException("Follow relationship not found"));

        followRepository.delete(follow);
    }

    public long getFollowersCount(Long userId) {
        return followRepository.countByFollowingId(userId);
    }

    public long getFollowingCount(Long userId) {
        return followRepository.countByFollowerId(userId);
    }
}
