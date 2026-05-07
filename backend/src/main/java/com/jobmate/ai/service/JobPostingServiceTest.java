package com.jobmate.ai.service;

import com.jobmate.ai.dto.job.JobPostingRequest;
import com.jobmate.ai.dto.job.JobPostingResponse;
import com.jobmate.ai.entity.JobPosting;
import com.jobmate.ai.entity.JobStatus;
import com.jobmate.ai.entity.User;
import com.jobmate.ai.repository.JobPostingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobPostingServiceTest {

    @Mock
    private JobPostingRepository jobPostingRepository;

    @InjectMocks
    private JobPostingService jobPostingService;

    @Test
    void createJob_ShouldSaveJobWithSavedStatus() {
        User user = User.builder()
                .id(1L)
                .email("test@example.com")
                .role("USER")
                .build();

        JobPostingRequest request = new JobPostingRequest(
                "ExampleTech",
                "Junior Java Developer",
                "Edinburgh, UK",
                "https://example.com/job",
                "Java Spring Boot PostgreSQL role"
        );

        when(jobPostingRepository.save(any(JobPosting.class))).thenAnswer(invocation -> {
            JobPosting job = invocation.getArgument(0);
            job.setId(1L);
            return job;
        });

        JobPostingResponse response = jobPostingService.createJob(user, request);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.companyName()).isEqualTo("ExampleTech");
        assertThat(response.jobTitle()).isEqualTo("Junior Java Developer");
        assertThat(response.status()).isEqualTo(JobStatus.SAVED);

        verify(jobPostingRepository).save(any(JobPosting.class));
    }
}
