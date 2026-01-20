package com.socio.socio.repository;

import com.socio.socio.model.PostReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostReportRepository extends JpaRepository<PostReport, Long> {
    List<PostReport> findByResolvedFalse();
}
