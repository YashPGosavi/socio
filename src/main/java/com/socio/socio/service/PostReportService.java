package com.socio.socio.service;

import com.socio.socio.exception.BadRequestException;
import com.socio.socio.exception.NotFoundException;
import com.socio.socio.model.Post;
import com.socio.socio.model.PostReport;
import com.socio.socio.model.User;
import com.socio.socio.repository.PostReportRepository;
import com.socio.socio.repository.PostRepository;
import com.socio.socio.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostReportService {

    private final PostReportRepository postReportRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostReportService(PostReportRepository postReportRepository,
                         PostRepository postRepository,
                         UserRepository userRepository
                        ) {
        this.postReportRepository = postReportRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public void reportPost(Long userId, Long postId, String reason) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        PostReport postReport = new PostReport();
        postReport.setPost(post);
        postReport.setReportedBy(user);
        postReport.setReason(reason);
        postReport.setResolved(false);
        postReport.setCreatedAt(java.time.LocalDateTime.now());
        postReportRepository.save(postReport);
    }

    public void resolveReport(Long reportId, boolean deletePost) {
        PostReport report = postReportRepository.findById(reportId)
                .orElseThrow(() -> new NotFoundException("Report not found"));

        if(!report.isResolved()) {
            throw new BadRequestException("Report is already resolved");
        }

        if (deletePost) {
            postRepository.delete(report.getPost());
        }

        report.setResolved(true);
        postReportRepository.save(report);
    }

    public List<PostReport> getReports(Long userId) {
        return postReportRepository.findByResolvedFalse();
    }


}
