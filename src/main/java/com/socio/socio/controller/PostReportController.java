package com.socio.socio.controller;

import com.socio.socio.service.PostReportService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
public class PostReportController {

    private final PostReportService reportService;

    public PostReportController(PostReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/{postId}")
    public String reportPost(@RequestAttribute Long userId,
                             @PathVariable Long postId,
                             @RequestParam String reason) {
        reportService.reportPost(userId, postId, reason);
        return "Post reported successfully";
    }
}
