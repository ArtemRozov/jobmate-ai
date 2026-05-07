package com.jobmate.ai.controller;

import com.jobmate.ai.dto.job.JobPostingRequest;
import com.jobmate.ai.dto.job.JobPostingResponse;
import com.jobmate.ai.dto.job.UpdateJobStatusRequest;
import com.jobmate.ai.entity.JobStatus;
import com.jobmate.ai.entity.User;
import com.jobmate.ai.service.JobPostingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobPostingController {

    private final JobPostingService jobPostingService;

    @PostMapping
    public JobPostingResponse createJob(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody JobPostingRequest request
    ) {
        return jobPostingService.createJob(user, request);
    }

    @GetMapping
    public List<JobPostingResponse> getMyJobs(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) JobStatus status
    ) {
        return jobPostingService.getMyJobs(user, status);
    }

    @GetMapping("/{id}")
    public JobPostingResponse getMyJobById(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        return jobPostingService.getMyJobById(user, id);
    }

    @PatchMapping("/{id}/status")
    public JobPostingResponse updateJobStatus(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @Valid @RequestBody UpdateJobStatusRequest request
    ) {
        return jobPostingService.updateJobStatus(user, id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteJob(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        jobPostingService.deleteJob(user, id);
    }
}
