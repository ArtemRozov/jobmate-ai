package com.jobmate.ai.dto.job;

import com.jobmate.ai.entity.JobStatus;

import java.time.LocalDateTime;

public record JobPostingResponse(
        Long id,
        String companyName,
        String jobTitle,
        String location,
        String jobUrl,
        String description,
        JobStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
