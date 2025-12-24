package com.kuangkuang.kuangkuang.service;

import com.kuangkuang.kuangkuang.pojo.dto.UserDto;
import com.kuangkuang.kuangkuang.pojo.entity.User;
import com.kuangkuang.kuangkuang.pojo.vo.UserVo;

public interface UserService {

    UserVo login(UserDto userDto);

    UserVo sign(UserDto userDto);

    void update(User user);


    User getUserById(int id);

    void sendVerificationCode(String email);

    UserVo loginByCode(String email, String code);
}
