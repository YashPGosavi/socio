package com.socio.socio.repository;

import com.socio.socio.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByUserIdInOrderByIdDesc(List<Long> userIds, Pageable pageable);
}



