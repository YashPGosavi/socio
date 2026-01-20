package com.socio.socio.repository;

import com.socio.socio.dto.UserStatsResponse;
import com.socio.socio.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFollowerIdAndFollowingId(Long followerId, Long followingId);

    long countByFollowerId(Long followerId);
    long countByFollowingId(Long followingId);

    @Query("""
            SELECT f.following.id
            FROM Follow f
            WHERE f.follower.id = :followerId
       """)
    List<Long> findFollowingIds(@Param("followerId") Long followerId);

    @Query("""
            SELECT new com.socio.socio.dto.UserStatsResponse(
                f.following.id,
                f.following.name,
                COUNT(f)
            )
            FROM Follow f
            GROUP BY f.following.id, f.following.name
            ORDER BY COUNT(f) DESC
        """)
    List<UserStatsResponse> findUsersOrderedByFollowers();
}
