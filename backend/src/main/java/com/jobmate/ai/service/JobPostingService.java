package com.jobmate.ai.service;

import com.jobmate.ai.dto.job.JobPostingRequest;
import com.jobmate.ai.dto.job.JobPostingResponse;
import com.jobmate.ai.dto.job.UpdateJobStatusRequest;
import com.jobmate.ai.entity.JobPosting;
import com.jobmate.ai.entity.JobStatus;
import com.jobmate.ai.entity.User;
import com.jobmate.ai.exception.ResourceNotFoundException;
import com.jobmate.ai.repository.JobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;

    public JobPostingResponse createJob(User user, JobPostingRequest request) {
        JobPosting jobPosting = JobPosting.builder()
                .companyName(request.companyName())
                .jobTitle(request.jobTitle())
                .location(request.location())
                .jobUrl(request.jobUrl())
                .description(request.description())
                .status(JobStatus.SAVED)
                .user(user)
                .build();

        JobPosting savedJob = jobPostingRepository.save(jobPosting);

        return mapToResponse(savedJob);
    }

    public List<JobPostingResponse> getMyJobs(User user, JobStatus status) {
        List<JobPosting> jobs;

        if (status != null) {
            jobs = jobPostingRepository.findAllByUserAndStatusOrderByCreatedAtDesc(user, status);
        } else {
            jobs = jobPostingRepository.findAllByUserOrderByCreatedAtDesc(user);
        }

        return jobs.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public JobPostingResponse getMyJobById(User user, Long jobId) {
        JobPosting jobPosting = findJobOwnedByUser(user, jobId);
        return mapToResponse(jobPosting);
    }

    public JobPostingResponse updateJobStatus(
            User user,
            Long jobId,
            UpdateJobStatusRequest request
    ) {
        JobPosting jobPosting = findJobOwnedByUser(user, jobId);

        jobPosting.setStatus(request.status());

        JobPosting savedJob = jobPostingRepository.save(jobPosting);

        return mapToResponse(savedJob);
    }

    public void deleteJob(User user, Long jobId) {
        JobPosting jobPosting = findJobOwnedByUser(user, jobId);
        jobPostingRepository.delete(jobPosting);
    }

    private JobPosting findJobOwnedByUser(User user, Long jobId) {
        return jobPostingRepository.findByIdAndUser(jobId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Job posting not found"));
    }

    private JobPostingResponse mapToResponse(JobPosting jobPosting) {
        return new JobPostingResponse(
                jobPosting.getId(),
                jobPosting.getCompanyName(),
                jobPosting.getJobTitle(),
                jobPosting.getLocation(),
                jobPosting.getJobUrl(),
                jobPosting.getDescription(),
                jobPosting.getStatus(),
                jobPosting.getCreatedAt(),
                jobPosting.getUpdatedAt()
        );
    }
}
