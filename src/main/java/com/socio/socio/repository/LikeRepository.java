package com.socio.socio.repository;

import com.socio.socio.dto.PostStatsResponse;
import com.socio.socio.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserIdAndPostId(Long userId, Long postId);

    long countByPostId(Long postId);

    @Query("""
            SELECT new com.socio.socio.dto.PostStatsResponse(
                l.post.id,
                l.post.content,
                COUNT(l)
            )
            FROM Like l
            GROUP BY l.post.id, l.post.content
            ORDER BY COUNT(l) DESC
        """)
    List<PostStatsResponse> findPostsOrderedByLikes();
}
