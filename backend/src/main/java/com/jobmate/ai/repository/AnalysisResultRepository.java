package com.jobmate.ai.repository;

import com.jobmate.ai.entity.AnalysisResult;
import com.jobmate.ai.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnalysisResultRepository extends JpaRepository<AnalysisResult, Long> {

    Optional<AnalysisResult> findByJobPosting(JobPosting jobPosting);

    Optional<AnalysisResult> findByJobPostingId(Long jobPostingId);
}
