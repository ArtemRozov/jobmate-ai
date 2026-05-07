package com.jobmate.ai.controller;

import com.jobmate.ai.dto.profile.ProfileRequest;
import com.jobmate.ai.dto.profile.ProfileResponse;
import com.jobmate.ai.entity.User;
import com.jobmate.ai.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/me")
    public ProfileResponse getMyProfile(@AuthenticationPrincipal User user) {
        return profileService.getMyProfile(user);
    }

    @PutMapping("/me")
    public ProfileResponse createOrUpdateMyProfile(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody ProfileRequest request
    ) {
        return profileService.createOrUpdateMyProfile(user, request);
    }
}
