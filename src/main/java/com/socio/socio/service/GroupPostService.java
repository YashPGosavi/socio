package com.socio.socio.service;

import com.socio.socio.exception.BadRequestException;
import com.socio.socio.exception.NotFoundException;
import com.socio.socio.model.*;
import com.socio.socio.repository.*;
import org.springframework.stereotype.Service;

@Service
public class GroupPostService {

    private final GroupRepository groupRepository;
    private final GroupMemberRepository memberRepository;
    private final GroupPostRepository postRepository;
    private final UserRepository userRepository;

    public GroupPostService(GroupRepository groupRepository,
                            GroupMemberRepository memberRepository,
                            GroupPostRepository postRepository,
                            UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public GroupPost createGroupPost(Long userId, Long groupId, String content) {

        if (memberRepository.findByGroupIdAndUserId(groupId, userId).isEmpty()) {
            throw new BadRequestException("You are not a group member");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group not found"));

        GroupPost post = new GroupPost();
        post.setContent(content);
        post.setUser(user);
        post.setGroup(group);

        return postRepository.save(post);
    }
}