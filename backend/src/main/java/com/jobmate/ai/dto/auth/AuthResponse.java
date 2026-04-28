package com.jobmate.ai.dto.auth;

public record AuthResponse(
        String token,
        String email,
        String role
) {
}
