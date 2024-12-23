package com.javasecurity.api.app.dto;

import lombok.Data;

@Data
public class LoginSearch {
    //아이디
    private String userId;

    //비밀번호
    private String password;
}
