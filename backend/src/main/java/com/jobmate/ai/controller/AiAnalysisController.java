package com.jobmate.ai.controller;

import com.jobmate.ai.dto.analysis.JobAnalysisResponse;
import com.jobmate.ai.entity.User;
import com.jobmate.ai.service.AiAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class AiAnalysisController {

    private final AiAnalysisService aiAnalysisService;

    @PostMapping("/{id}/analyze")
    public JobAnalysisResponse analyzeJob(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        return aiAnalysisService.analyzeJob(user, id);
    }
}
