package com.socio.socio.service;

import com.socio.socio.exception.BadRequestException;
import com.socio.socio.exception.NotFoundException;
import com.socio.socio.model.*;
import com.socio.socio.repository.*;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMemberRepository memberRepository;
    private final UserRepository userRepository;

    public GroupService(GroupRepository groupRepository,
                        GroupMemberRepository memberRepository,
                        UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
    }

    public Group createGroup(Long userId, String name, boolean isPrivate) {

        User admin = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Group group = new Group();
        group.setName(name);
        group.setPrivate(isPrivate);
        group.setAdmin(admin);

        Group savedGroup = groupRepository.save(group);

        GroupMember member = new GroupMember();
        member.setGroup(savedGroup);
        member.setUser(admin);

        memberRepository.save(member);

        return savedGroup;
    }

    public void joinGroup(Long userId, Long groupId) {

        if (memberRepository.findByGroupIdAndUserId(groupId, userId).isPresent()) {
            throw new BadRequestException("Already a member");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group not found"));

        GroupMember member = new GroupMember();
        member.setGroup(group);
        member.setUser(user);

        memberRepository.save(member);
    }

    public void leaveGroup(Long userId, Long groupId) {

        GroupMember member = memberRepository
                .findByGroupIdAndUserId(groupId, userId)
                .orElseThrow(() -> new BadRequestException("Not a group member"));

        memberRepository.delete(member);
    }
}