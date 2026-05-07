package com.jobmate.ai.service;

import com.jobmate.ai.dto.profile.ProfileRequest;
import com.jobmate.ai.dto.profile.ProfileResponse;
import com.jobmate.ai.entity.Profile;
import com.jobmate.ai.entity.User;
import com.jobmate.ai.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileResponse getMyProfile(User user) {
        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        return mapToResponse(profile);
    }

    public ProfileResponse createOrUpdateMyProfile(User user, ProfileRequest request) {
        Profile profile = profileRepository.findByUser(user)
                .orElseGet(() -> Profile.builder()
                        .user(user)
                        .build()
                );

        profile.setFullName(request.fullName());
        profile.setHeadline(request.headline());
        profile.setSummary(request.summary());
        profile.setLocation(request.location());
        profile.setExperience(request.experience());
        profile.setSkills(request.skills());
        profile.setGithubUrl(request.githubUrl());
        profile.setLinkedinUrl(request.linkedinUrl());
        profile.setPortfolioUrl(request.portfolioUrl());

        Profile savedProfile = profileRepository.save(profile);

        return mapToResponse(savedProfile);
    }

    private ProfileResponse mapToResponse(Profile profile) {
        return new ProfileResponse(
                profile.getId(),
                profile.getFullName(),
                profile.getHeadline(),
                profile.getSummary(),
                profile.getLocation(),
                profile.getExperience(),
                profile.getSkills(),
                profile.getGithubUrl(),
                profile.getLinkedinUrl(),
                profile.getPortfolioUrl(),
                profile.getCreatedAt(),
                profile.getUpdatedAt()
        );
    }
}
