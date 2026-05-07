package com.jobmate.ai.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobmate.ai.dto.analysis.AiJobAnalysisDto;
import com.jobmate.ai.dto.analysis.JobAnalysisResponse;
import com.jobmate.ai.entity.AnalysisResult;
import com.jobmate.ai.entity.JobPosting;
import com.jobmate.ai.entity.Profile;
import com.jobmate.ai.entity.User;
import com.jobmate.ai.exception.AiAnalysisException;
import com.jobmate.ai.exception.ResourceNotFoundException;
import com.jobmate.ai.repository.AnalysisResultRepository;
import com.jobmate.ai.repository.JobPostingRepository;
import com.jobmate.ai.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import dev.langchain4j.model.chat.ChatModel;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiAnalysisService {

    private final ChatModel chatModel;
    private final ObjectMapper objectMapper;
    private final ProfileRepository profileRepository;
    private final JobPostingRepository jobPostingRepository;
    private final AnalysisResultRepository analysisResultRepository;

    public JobAnalysisResponse analyzeJob(User user, Long jobId) {
        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found. Please create your profile first."));

        JobPosting jobPosting = jobPostingRepository.findByIdAndUser(jobId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Job posting not found"));

        String prompt = buildPrompt(profile, jobPosting);

        try {
            String json = chatModel.chat(prompt);

            AiJobAnalysisDto aiResult = objectMapper.readValue(json, AiJobAnalysisDto.class);

            AnalysisResult analysisResult = analysisResultRepository
                    .findByJobPosting(jobPosting)
                    .orElseGet(() -> AnalysisResult.builder()
                            .jobPosting(jobPosting)
                            .build());

            analysisResult.setMatchScore(aiResult.matchScore());
            analysisResult.setKeyRequirements(toJson(aiResult.keyRequirements()));
            analysisResult.setMissingSkills(toJson(aiResult.missingSkills()));
            analysisResult.setTailoredCvSummary(aiResult.tailoredCvSummary());
            analysisResult.setCoverLetter(aiResult.coverLetter());
            analysisResult.setInterviewQuestions(toJson(aiResult.interviewQuestions()));
            analysisResult.setPreparationPlan(toJson(aiResult.preparationPlan()));

            AnalysisResult savedResult = analysisResultRepository.save(analysisResult);

            return mapToResponse(savedResult);

        } catch (Exception e) {
            throw new AiAnalysisException("Failed to analyze job posting with AI", e);
        }
    }

    public JobAnalysisResponse getAnalysisResult(User user, Long jobId) {
        JobPosting jobPosting = jobPostingRepository.findByIdAndUser(jobId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Job posting not found"));

        AnalysisResult analysisResult = analysisResultRepository.findByJobPosting(jobPosting)
                .orElseThrow(() -> new ResourceNotFoundException("Analysis result not found"));

        return mapToResponse(analysisResult);
    }

    private JobAnalysisResponse mapToResponse(AnalysisResult result) {
        return new JobAnalysisResponse(
                result.getId(),
                result.getMatchScore(),
                fromJson(result.getKeyRequirements()),
                fromJson(result.getMissingSkills()),
                result.getTailoredCvSummary(),
                result.getCoverLetter(),
                fromJson(result.getInterviewQuestions()),
                fromJson(result.getPreparationPlan()),
                result.getCreatedAt()
        );
    }

    private String toJson(List<String> list) {
        try {
            return objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize list", e);
        }
    }

    private List<String> fromJson(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize list", e);
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