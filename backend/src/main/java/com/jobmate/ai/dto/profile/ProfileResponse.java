package com.jobmate.ai.dto.profile;

import java.time.LocalDateTime;

public record ProfileResponse(
        Long id,
        String fullName,
        String headline,
        String summary,
        String location,
        String experience,
        String skills,
        String githubUrl,
        String linkedinUrl,
        String portfolioUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
