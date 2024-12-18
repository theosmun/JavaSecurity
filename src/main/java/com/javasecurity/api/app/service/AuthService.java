package com.javasecurity.api.app.service;

import com.javasecurity.api.app.dto.LoginInfo;
import com.javasecurity.api.app.dto.LoginSearch;
import com.javasecurity.api.app.entity.User;
import com.javasecurity.api.app.mapper.UserMapper;
//import com.javasecurity.api.base.security.JwtTokenUtils;
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
    //private final PasswordEncoder passwordEncoder;
    //private final JwtTokenUtils jwtUtil;

    public void register(User userinfo) {
        log.info(userinfo.getUserId(),userinfo.getPassword());
        User user = userMapper.getUserByUserId(userinfo.getUserId());
        log.info(user.getUserId(),user.getPassword());
        if (user != null) {
            throw new IllegalStateException("이미 계정이 존재합니다");
        }

//        String password = passwordEncoder.encode(userinfo.getPassword());
//        userinfo.setPassword(password);
//
//        userMapper.saveUserInfo(userinfo);
    }

//    public LoginInfo login(LoginSearch search) {
//        User user = userMapper.getUserByUserId(search.getUserId());
//        if (user == null) {
//            log.info("[AuthContoller.login] ==> User Not Found!");
//            throw new UsernameNotFoundException("사용자 정보를 찾을 수 없습니다.");
//        }
//
//        // 비밀번호 검증
//        if (!passwordEncoder.matches(search.getPassword(), user.getPassword())) {
//            log.info("[AuthContoller.login] ==> Password Not Match!");
//            throw new UsernameNotFoundException("사용자 정보를 찾을 수 없습니다.");
//        }
//
//        // 토큰 발급
//        String uuid = UUID.randomUUID().toString();
//        String accessToken = jwtUtil.createToken(search.getUserId(), uuid, true);
//        String refreshToken = jwtUtil.createToken(search.getUserId(), uuid, false);
//
//        // 토큰 저장
//        user.setRefreshToken(refreshToken);
//        userMapper.saveUserInfo(user);
//
//        // 토큰 정보 반환
//        return LoginInfo.builder()
//                .accessToken(accessToken)
//                .refreshToken(refreshToken)
//                .build();
//    }
}