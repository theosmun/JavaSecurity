package com.javasecurity.api.app.controller;

import com.javasecurity.api.app.constant.CommonConstant;
import com.javasecurity.api.app.dto.LoginInfo;
import com.javasecurity.api.app.dto.LoginSearch;
import com.javasecurity.api.app.entity.User;
import com.javasecurity.api.app.service.AuthService;
import com.javasecurity.api.base.entity.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;

    // 회원가입 요청을 처리하는 엔드포인트
    @PostMapping("/register")
    public Response<String> register(@RequestBody User userinfo) {
        // 회원가입 서비스를 호출하여 사용자 정보를 처리
        authService.register(userinfo);

        // 성공 메시지를 담아 응답 반환
        return new Response<String>().setPayload(CommonConstant.SUCCESS);
    }

    // 로그인 요청을 처리하는 엔드포인트
    @PostMapping("/login")
    public Response<LoginInfo> login(@RequestBody LoginSearch search) {
        // 로그인 서비스를 호출하여 로그인 정보 생성
        LoginInfo loginInfo = authService.login(search);

        // 로그인 정보를 담아 응답 반환
        return new Response<LoginInfo>().setPayload(loginInfo);
    }

}
