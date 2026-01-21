package com.socio.socio.controller;

import com.socio.socio.model.GroupPost;
import com.socio.socio.service.GroupPostService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group-posts")
public class GroupPostController {

    private final GroupPostService groupPostService;

    public GroupPostController(GroupPostService groupPostService) {
        this.groupPostService = groupPostService;
    }

    @PostMapping("/{groupId}")
    public GroupPost create(@RequestAttribute Long userId,
                            @PathVariable Long groupId,
                            @RequestParam String content) {
        return groupPostService.createGroupPost(userId, groupId, content);
    }
}