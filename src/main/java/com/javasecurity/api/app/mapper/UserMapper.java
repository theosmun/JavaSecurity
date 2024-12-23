package com.javasecurity.api.app.mapper;

import com.javasecurity.api.app.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    // 사용자 ID로 사용자 정보를 조회하는 메서드
    User getUserByUserId(String userId);

    // 사용자 정보를 저장하는 메서드
    void saveUserInfo(User userInfo);
}
