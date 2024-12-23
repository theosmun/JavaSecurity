package com.javasecurity.api.base.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    // JWT 인증 필터를 필드 선언
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 보호를 비활성화. (REST API의 경우 보통 Stateless이므로 CSRF를 사용하지 않음)
        http.csrf(AbstractHttpConfigurer::disable)
                // 요청 권한 설정
                .authorizeHttpRequests(authorize -> authorize
                        // "/api/auth/**" 경로는 모든 사용자에게 허용
                        .requestMatchers("/api/auth/**").permitAll()
                        // 그 외의 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                )
                // JWT 인증 필터를 UsernamePasswordAuthenticationFilter 앞에 추가
               .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        // 설정을 마친 SecurityFilterChain을 반환
        return http.build();
    }

    // BCryptPasswordEncoder를 Bean으로 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 비밀번호 암호화 및 검증을 위한 BCryptPasswordEncoder 인스턴스 생성
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager( AuthenticationConfiguration configuration) throws Exception {
        // AuthenticationManager를 AuthenticationConfiguration에서 가져와 Bean으로 등록
        return configuration.getAuthenticationManager();
    }
}
