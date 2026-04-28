package com.jobmate.ai.controller;

import com.jobmate.ai.entity.User;
import com.jobmate.ai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserTestController {

    private final UserRepository userRepository;

    @PostMapping
    public User createUser() {
        User user = User.builder()
                .email("test@example.com")
                .password("1234")
                .role("USER")
                .build();

        return userRepository.save(user);
    }
}
