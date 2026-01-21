package com.socio.socio.repository;

import com.socio.socio.dto.PostStatsResponse;
import com.socio.socio.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);

    @Query("""
            SELECT new com.socio.socio.dto.PostStatsResponse(
                c.post.id,
                c.post.content,
                COUNT(c)
            )
            FROM Comment c
            GROUP BY c.post.id, c.post.content
            ORDER BY COUNT(c) DESC
        """)
    List<PostStatsResponse> findPostsOrderedByComments();
}
