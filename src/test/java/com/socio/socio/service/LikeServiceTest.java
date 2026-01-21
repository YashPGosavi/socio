package com.socio.socio.service;

import com.socio.socio.exception.BadRequestException;
import com.socio.socio.model.Like;
import com.socio.socio.repository.LikeRepository;
import com.socio.socio.repository.PostRepository;
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
class LikeServiceTest {

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private LikeService likeService;

    @Test
    void shouldThrowExceptionIfPostAlreadyLiked() {

        // GIVEN
        Long userId = 1L;
        Long postId = 2L;

        when(likeRepository.findByUserIdAndPostId(userId, postId))
                .thenReturn(Optional.of(new Like()));

        // WHEN + THEN
        assertThrows(
                BadRequestException.class,
                () -> likeService.likePost(userId, postId)
        );

        verify(likeRepository, never()).save(any());
    }

    @Test
    void shouldLikePostSuccessfully() {

        Long userId = 1L;
        Long postId = 2L;

        when(likeRepository.findByUserIdAndPostId(userId, postId))
                .thenReturn(Optional.empty());

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(new com.socio.socio.model.User()));

        when(postRepository.findById(postId))
                .thenReturn(Optional.of(new com.socio.socio.model.Post()));

        likeService.likePost(userId, postId);

        verify(likeRepository).save(any(Like.class));
    }
}