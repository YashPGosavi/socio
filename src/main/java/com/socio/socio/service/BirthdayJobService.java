package com.socio.socio.service;

import com.socio.socio.model.User;
import com.socio.socio.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BirthdayJobService {

    private final UserRepository userRepository;
    private final PostService postService;

    public BirthdayJobService(UserRepository userRepository, PostService postService) {
        this.userRepository = userRepository;
        this.postService = postService;
    }

    public void createBirthdayPosts() {
        List<User> users = userRepository.findUsersWithBirthdayToday(LocalDate.now());

        for(User user : users) {
            postService.createPost(
                    user.getId(),
                    "🎉 Happy Birthday " + user.getName() + "! 🎂"
            );
        }
    }
}
