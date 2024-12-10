package com.javasecurity.api.app.mapper;

import com.javasecurity.api.app.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User getUserByUserId(String userId );

    void saveUserInfo(User userInfo);
}
