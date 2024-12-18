//package com.javasecurity.api.base.security;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.javasecurity.api.app.entity.User;
//import com.javasecurity.api.app.mapper.UserMapper;
//import com.javasecurity.api.base.entity.Response;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//
//import java.io.IOException;
//import java.util.Collections;
//
//@Slf4j
//@RequiredArgsConstructor
//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//    private final UserMapper userMapper;
//    private final JwtTokenUtils jwtUtil;
//
//    private static final String BEARER = "Bearer";
//    private static final String GAP = " ";
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        try {
//            // /api/auth/refresh 경로에서 필터를 실행하지 않도록 예외 처리
//            if (request.getRequestURI().startsWith("/api/auth")) {
//                filterChain.doFilter(request, response); // 필터를 건너뛰고 다음 필터로 이동
//                return;
//            }
//
//            String authHeader = request.getHeader("Authorization");
//            if (authHeader != null) {
//                String token = authHeader.startsWith(BEARER) ? authHeader.split(GAP)[1] : authHeader;
//
//                // 토큰 정보
//                Claims claims = jwtUtil.getTokenInfo(token);
//                String userId = claims.get("USER_ID").toString();
//
//                // 사용자 정보
//                User userInfo = userMapper.getUserByUserId(userId);
//
//                // 인증
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userInfo, null, Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//
//            // 다음 필터
//            filterChain.doFilter(request, response);
//        } catch (Exception e) {
//            log.info(e.getMessage());
//        }
//    }
//}