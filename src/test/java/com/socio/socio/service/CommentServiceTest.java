package com.socio.socio.service;

import com.socio.socio.exception.BadRequestException;
import com.socio.socio.exception.NotFoundException;
import com.socio.socio.model.Comment;
import com.socio.socio.model.Post;
import com.socio.socio.model.User;
import com.socio.socio.repository.CommentRepository;
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
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    void shouldThrowExceptionWhenDeletingOthersComment() {

        // GIVEN
        Long loggedInUserId = 1L;
        Long commentOwnerId = 2L;

        User owner = new User();
        owner.setId(commentOwnerId);

        Comment comment = new Comment();
        comment.setUser(owner);

        when(commentRepository.findById(10L))
                .thenReturn(Optional.of(comment));

        // WHEN + THEN
        assertThrows(
                BadRequestException.class,
                () -> commentService.deleteComment(loggedInUserId, 10L)
        );

        verify(commentRepository, never()).delete(any());
    }

    @Test
    void shouldDeleteOwnCommentSuccessfully() {

        // GIVEN
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        Comment comment = new Comment();
        comment.setUser(user);

        when(commentRepository.findById(10L))
                .thenReturn(Optional.of(comment));

        // WHEN
        commentService.deleteComment(userId, 10L);

        // THEN
        verify(commentRepository).delete(comment);
    }

    @Test
    void shouldAddCommentSuccessfully() {

        // GIVEN
        Long userId = 1L;
        Long postId = 2L;

        User user = new User();
        Post post = new Post();

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(postRepository.findById(postId))
                .thenReturn(Optional.of(post));

        // WHEN
        commentService.addComment(userId, postId, "Nice post");

        // THEN
        verify(commentRepository).save(any(Comment.class));
    }
}