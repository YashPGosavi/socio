package com.socio.socio.controller;

import com.socio.socio.model.Group;
import com.socio.socio.service.GroupService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public Group createGroup(@RequestAttribute Long userId,
                             @RequestParam String name,
                             @RequestParam boolean isPrivate) {
        return groupService.createGroup(userId, name, isPrivate);
    }

    @PostMapping("/{groupId}/join")
    public String join(@RequestAttribute Long userId,
                       @PathVariable Long groupId) {
        groupService.joinGroup(userId, groupId);
        return "Joined group";
    }

    @DeleteMapping("/{groupId}/leave")
    public String leave(@RequestAttribute Long userId,
                        @PathVariable Long groupId) {
        groupService.leaveGroup(userId, groupId);
        return "Left group";
    }
}