package com.javasecurity.api.base.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenUtils {
    // JWT 서명에 사용될 비밀 키 (application.yml 또는 properties 파일에서 주입)
    @Value("${jwt.secretKey}")
    private String secretKey;

    // 액세스 토큰의 만료 시간 (application.yml 또는 properties 파일에서 주입됨)
    @Value("${jwt.accessToken.expiration}")
    private Long accessTokenExpire;

    // 리프레시 토큰의 만료 시간 (application.yml 또는 properties 파일에서 주입됨)
    @Value("${jwt.refreshToken.expiration}")
    private Long refreshTokenExpire;

    // HMAC 알고리즘에 사용할 SecretKey 객체
    private SecretKey tokenSecretKey;

    // 객체 초기화 후 실행되는 메서드
    @PostConstruct
    protected void init() {
        // Base64로 인코딩된 secretKey를 HmacSHA256 알고리즘용 SecretKey 객체로 변환
        this.tokenSecretKey = new SecretKeySpec(Base64.getEncoder().encode(secretKey.getBytes()), "HmacSHA256");
    }

    /**
     * JWT 토큰을 생성하는 메서드
     *
     * @param userId        사용자 ID
     * @param uuid          고유 식별자 (JWT의 JTI 필드에 저장)
     * @param isAccessToken true이면 액세스 토큰, false이면 리프레시 토큰 생성
     * @return 생성된 JWT 토큰 문자열
     */
    public String createToken(String userId, String uuid, boolean isAccessToken) {
        // 토큰 만료 시간을 결정 (액세스 토큰 또는 리프레시 토큰)
        Long expire = isAccessToken ? accessTokenExpire : refreshTokenExpire;

        // JWT 빌더를 사용해 토큰 생성
        return Jwts.builder()
                .setId(uuid) // JTI 필드에 uuid 설정 (토큰 고유 식별자)
                .claim("ID", uuid) // 커스텀 클레임 추가 (고유 식별자)
                .claim("USER_ID", userId) // 커스텀 클레임 추가 (사용자 ID)
                .setSubject(userId) // 토큰의 주제 설정 (사용자 ID)
                .setIssuedAt(new Date()) // 토큰 발급 시간 설정
                .setExpiration(new Date(System.currentTimeMillis() + expire)) // 만료 시간 설정
                .signWith(tokenSecretKey) // HMAC-SHA256 알고리즘으로 서명
                .compact(); // JWT 문자열 생성
    }

    /**
     * JWT 토큰을 파싱하여 클레임 정보를 가져오는 메서드
     *
     * @param token JWT 토큰 문자열
     * @return JWT 클레임 (Claims 객체)
     * @throws io.jsonwebtoken.JwtException 토큰이 유효하지 않거나 만료된 경우 예외 발생
     */
    public Claims getTokenInfo(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(tokenSecretKey) // 서명 검증에 사용할 SecretKey 설정
                .build() // JWT 파서 생성
                .parseClaimsJws(token) // 토큰 검증 및 파싱
                .getBody(); // JWT의 클레임 반환
    }
}