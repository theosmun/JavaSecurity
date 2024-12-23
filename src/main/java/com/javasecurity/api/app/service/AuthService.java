package com.javasecurity.api.app.service;

import com.javasecurity.api.app.dto.LoginInfo;
import com.javasecurity.api.app.dto.LoginSearch;
import com.javasecurity.api.app.entity.User;
import com.javasecurity.api.app.mapper.UserMapper;
//import com.javasecurity.api.base.security.JwtTokenUtils;
import com.javasecurity.api.base.advice.ApiError;
import com.javasecurity.api.base.exception.CustomException;
import com.javasecurity.api.base.security.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@RequiredArgsConstructor
@Service
@Slf4j
public class AuthService {
    public final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtUtil;

    public void register(User userinfo) {
        User user = userMapper.getUserByUserId(userinfo.getUserId());
        if (user != null) {
            throw new CustomException(ApiError.USER_READY);
        }

        String password = passwordEncoder.encode(userinfo.getPassword());
        userinfo.setPassword(password);

        userMapper.saveUserInfo(userinfo);
  }

    public LoginInfo login(LoginSearch search) {
        // 사용자 ID로 사용자 정보 조회
        User user = userMapper.getUserByUserId(search.getUserId());
        // 사용자 정보가 없으면 예외 발생
        if (user == null) {
            throw new UsernameNotFoundException("사용자 정보를 찾을 수 없습니다.");
        }

        // 입력된 비밀번호와 저장된 비밀번호를 검증
        if (!passwordEncoder.matches(search.getPassword(), user.getPassword())) {
            // 비밀번호가 일치하지 않으면 CustomException 발생
            throw new CustomException(ApiError.USER_NOT_PASSWORD);
        }

        // 토큰 발급을 위한 UUID 생성
        String uuid = UUID.randomUUID().toString();
        // 액세스 토큰 생성 (로그인 상태에서 사용)
        String accessToken = jwtUtil.createToken(search.getUserId(), uuid, true);
        // 리프레시 토큰 생성 (토큰 갱신에 사용)
        String refreshToken = jwtUtil.createToken(search.getUserId(), uuid, false);

        // 생성된 리프레시 토큰을 사용자 정보에 저장
        user.setRefreshToken(refreshToken);
        // 업데이트된 사용자 정보를 데이터베이스에 저장
        userMapper.saveUserInfo(user);

        // 발급된 액세스 토큰과 리프레시 토큰 정보를 반환
        return LoginInfo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}