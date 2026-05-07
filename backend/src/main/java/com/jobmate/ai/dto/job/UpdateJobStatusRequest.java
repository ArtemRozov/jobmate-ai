package com.jobmate.ai.dto.job;

import com.jobmate.ai.entity.JobStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateJobStatusRequest(
        @NotNull
        JobStatus status
) {
}
