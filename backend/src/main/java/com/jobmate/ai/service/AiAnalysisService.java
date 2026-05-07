package com.jobmate.ai.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobmate.ai.dto.analysis.JobAnalysisResponse;
import com.jobmate.ai.entity.JobPosting;
import com.jobmate.ai.entity.Profile;
import com.jobmate.ai.entity.User;
import com.jobmate.ai.repository.JobPostingRepository;
import com.jobmate.ai.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import dev.langchain4j.model.chat.ChatModel;

@Service
@RequiredArgsConstructor
public class AiAnalysisService {

    private final ChatModel  chatModel;
    private final ObjectMapper objectMapper;
    private final ProfileRepository profileRepository;
    private final JobPostingRepository jobPostingRepository;

    public JobAnalysisResponse analyzeJob(User user, Long jobId) {
        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found. Please create your profile first."));

        JobPosting jobPosting = jobPostingRepository.findByIdAndUser(jobId, user)
                .orElseThrow(() -> new RuntimeException("Job posting not found"));

        String prompt = buildPrompt(profile, jobPosting);

        try {
            String json = chatModel.chat(prompt);
            return objectMapper.readValue(json, JobAnalysisResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to analyze job posting with AI", e);
        }
    }

    private String buildPrompt(Profile profile, JobPosting jobPosting) {
        return """
                You are JobMate AI, an assistant that analyzes job postings for job seekers.

                Analyze the job posting against the user profile.

                Return ONLY valid JSON.
                Do not include markdown.
                Do not include explanations outside JSON.

                JSON structure:
                {
                  "matchScore": 0,
                  "keyRequirements": [],
                  "missingSkills": [],
                  "tailoredCvSummary": "",
                  "coverLetter": "",
                  "interviewQuestions": [],
                  "preparationPlan": []
                }

                Rules:
                - matchScore must be an integer from 0 to 100.
                - keyRequirements should include the most important job requirements.
                - missingSkills should include skills the user lacks or should improve.
                - tailoredCvSummary should be short and suitable for a CV profile section.
                - coverLetter should be concise, professional and tailored to the job.
                - interviewQuestions should include likely interview questions.
                - preparationPlan should be a 7-day preparation plan.
                - Use clear and practical language.

                User profile:
                Full name: %s
                Headline: %s
                Summary: %s
                Location: %s
                Experience: %s
                Skills: %s
                GitHub: %s
                LinkedIn: %s
                Portfolio: %s

                Job posting:
                Company: %s
                Title: %s
                Location: %s
                URL: %s
                Description:
                %s
                """.formatted(
                profile.getFullName(),
                profile.getHeadline(),
                profile.getSummary(),
                profile.getLocation(),
                profile.getExperience(),
                profile.getSkills(),
                profile.getGithubUrl(),
                profile.getLinkedinUrl(),
                profile.getPortfolioUrl(),
                jobPosting.getCompanyName(),
                jobPosting.getJobTitle(),
                jobPosting.getLocation(),
                jobPosting.getJobUrl(),
                jobPosting.getDescription()
        );
    }
}