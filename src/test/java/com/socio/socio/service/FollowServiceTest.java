package com.socio.socio.service;

import com.socio.socio.exception.BadRequestException;
import com.socio.socio.model.Follow;
import com.socio.socio.model.User;
import com.socio.socio.repository.FollowRepository;
import com.socio.socio.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {

    @Mock
    private FollowRepository followRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FollowService followService;

    @Test
    void shouldThrowExceptionWhenUserFollowsSelf() {

        Long userId = 1L;

        assertThrows(
                BadRequestException.class,
                () -> followService.followUser(userId, userId)
        );

        verifyNoInteractions(followRepository);
    }

    @Test
    void shouldThrowExceptionWhenAlreadyFollowing() {

        Long followerId = 1L;
        Long followingId = 2L;

        when(followRepository.findByFollowerIdAndFollowingId(followerId, followingId))
                .thenReturn(Optional.of(new Follow()));

        assertThrows(
                BadRequestException.class,
                () -> followService.followUser(followerId, followingId)
        );

        verify(followRepository, never()).save(any());
    }

    @Test
    void shouldFollowUserSuccessfully() {

        Long followerId = 1L;
        Long followingId = 2L;

        User follower = new User();
        User following = new User();

        when(followRepository.findByFollowerIdAndFollowingId(followerId, followingId))
                .thenReturn(Optional.empty());

        when(userRepository.findById(followerId))
                .thenReturn(Optional.of(follower));

        when(userRepository.findById(followingId))
                .thenReturn(Optional.of(following));

        followService.followUser(followerId, followingId);

        verify(followRepository).save(any(Follow.class));
    }

    @Test
    void shouldUnfollowSuccessfully() {

        Long followerId = 1L;
        Long followingId = 2L;

        Follow follow = new Follow();

        when(followRepository.findByFollowerIdAndFollowingId(followerId, followingId))
                .thenReturn(Optional.of(follow));

        followService.unfollowUser(followerId, followingId);

        verify(followRepository).delete(follow);
    }
}