package com.jobmate.ai.controller;

import com.jobmate.ai.dto.analysis.JobAnalysisResponse;
import com.jobmate.ai.entity.User;
import com.jobmate.ai.service.AiAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}/analysis")
    public JobAnalysisResponse getAnalysisResult(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        return aiAnalysisService.getAnalysisResult(user, id);
    }
}
