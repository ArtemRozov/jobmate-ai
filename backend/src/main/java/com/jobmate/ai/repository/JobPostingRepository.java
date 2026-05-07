package com.jobmate.ai.repository;

import com.jobmate.ai.entity.JobPosting;
import com.jobmate.ai.entity.JobStatus;
import com.jobmate.ai.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

    List<JobPosting> findAllByUserOrderByCreatedAtDesc(User user);

    List<JobPosting> findAllByUserAndStatusOrderByCreatedAtDesc(User user, JobStatus status);

    Optional<JobPosting> findByIdAndUser(Long id, User user);
}
