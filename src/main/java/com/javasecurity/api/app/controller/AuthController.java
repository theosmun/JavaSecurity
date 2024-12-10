package com.javasecurity.api.app.controller;

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

    @PostMapping("/register")
    public Response<String> register(@RequestBody User user){
        authService.register(user);
        return new Response<String>().setPayload("SUCCESS");
    }

    @PostMapping("/login")
    public Response<LoginInfo> login(@RequestBody LoginSearch search) {
        LoginInfo loginInfo = authService.login(search);
        return new Response<LoginInfo>().setPayload(loginInfo);
    }

}
