package com.javasecurity.api.app.entity;

import com.javasecurity.api.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {
    // 사용자 번호
    private Long userNo;

    // 아이디
    private String userId;

    // 비밀번호
    private String password;

    // 리프레쉬 토큰
    private String refreshToken;

    // 비고
    private String remark;
}
