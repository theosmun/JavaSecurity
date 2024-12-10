package com.javasecurity.api.app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginInfo {
    // Access Token
    private String accessToken;

    // Refresh Token
    private String refreshToken;
}
