package com.jobmate.ai.dto.analysis;

import java.util.List;

public record AiJobAnalysisDto(
        int matchScore,
        List<String> keyRequirements,
        List<String> missingSkills,
        String tailoredCvSummary,
        String coverLetter,
        List<String> interviewQuestions,
        List<String> preparationPlan
) {
}
