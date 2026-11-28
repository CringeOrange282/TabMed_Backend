package com.example.demo.dto;

import java.util.UUID;

public record AuthResponse(
        String firstName,
        String secondName,
        UUID id,
        String email,
        String role,
        String token,
        String refreshToken
) {
}
