package com.javasecurity.api.base.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javasecurity.api.app.entity.User;
import com.javasecurity.api.app.mapper.UserMapper;
import com.javasecurity.api.base.entity.Response;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Component
// JWT 인증을 처리하는 필터 클래스
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // 데이터베이스에서 사용자 정보를 조회하기 위한 매퍼
    private final UserMapper userMapper;

    // JWT 토큰 유틸리티 클래스 (토큰 검증 및 파싱 기능 제공)
    private final JwtTokenUtils jwtUtil;

    // Authorization 헤더의 접두사 "Bearer"를 정의
    private static final String BEARER = "Bearer";

    // Bearer와 토큰 사이의 공백을 정의
    private static final String GAP = " ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // "/api/auth" 경로에 대해 이 필터를 건너뛰도록 설정
            if (request.getRequestURI().startsWith("/api/auth")) {
                // 필터를 실행하지 않고 다음 필터로 바로 이동
                filterChain.doFilter(request, response);
                return;
            }

            // Authorization 헤더에서 JWT 토큰을 추출
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null) {
                // Authorization 헤더가 "Bearer"로 시작하면 토큰만 추출
                String token = authHeader.startsWith(BEARER) ? authHeader.split(GAP)[1] : authHeader;

                // 토큰에서 클레임(Claims) 정보 추출
                Claims claims = jwtUtil.getTokenInfo(token);
                // 클레임에서 사용자 ID(USER_ID) 추출
                String userId = claims.get("USER_ID").toString();

                // 데이터베이스에서 사용자 정보 조회
                User userInfo = userMapper.getUserByUserId(userId);

                // Spring Security의 인증 객체 생성
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userInfo, // 인증 주체 (Principal)
                        null, // 인증 자격 증명 (Credentials) - JWT 인증이므로 null
                        Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")) // 권한 (Authority)
                );

                // Security Context에 인증 정보 설정
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            // 다음 필터로 요청 전달
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            // 예외 발생 시 로그 출력 (예: 토큰 검증 실패, 사용자 정보 조회 실패 등)
            log.info(e.getMessage());
        }
    }
}
