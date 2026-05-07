package com.jobmate.ai.dto.job;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record JobPostingRequest(

        @NotBlank
        @Size(max = 150)
        String companyName,

        @NotBlank
        @Size(max = 150)
        String jobTitle,

        @Size(max = 150)
        String location,

        String jobUrl,

        @NotBlank
        String description
) {
}
