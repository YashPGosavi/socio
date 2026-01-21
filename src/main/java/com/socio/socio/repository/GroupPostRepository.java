package com.socio.socio.repository;

import com.socio.socio.model.GroupPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupPostRepository extends JpaRepository<GroupPost, Long> {

    List<GroupPost> findByGroupId(Long groupId);
}