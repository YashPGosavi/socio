package com.socio.socio.controller;

import com.socio.socio.dto.FeedPostResponse;
import com.socio.socio.service.FeedService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feed")
public class FeedController {

    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping
    public List<FeedPostResponse> getFeed(
            @RequestAttribute Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return feedService.getFeed(userId, page, size);
    }
}
