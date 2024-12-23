package com.javasecurity.api.base.advice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum ApiError {
    // 사용자
    USER_NOT_FOUND("7000", "User Not Found"),
    USER_READY("7001", "User Ready"),
    USER_NOT_MATCH("7002", "User Not Match Information"),
    USER_NOT_PASSWORD("7003", "User Not Password"),

    // Token
    TOKEN_INVALID("4000", "Invalid Token"),
    TOKEN_EXPIRED("4001", "Token Expired");

    private final String code;
    private final String message;
}