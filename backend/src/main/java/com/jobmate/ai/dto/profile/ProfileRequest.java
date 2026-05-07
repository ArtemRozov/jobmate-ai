package com.jobmate.ai.dto.profile;

import jakarta.validation.constraints.Size;

public record ProfileRequest(

        @Size(max = 100)
        String fullName,

        @Size(max = 150)
        String headline,

        String summary,

        @Size(max = 100)
        String location,

        String experience,

        String skills,

        String githubUrl,

        String linkedinUrl,

        String portfolioUrl
) {
}