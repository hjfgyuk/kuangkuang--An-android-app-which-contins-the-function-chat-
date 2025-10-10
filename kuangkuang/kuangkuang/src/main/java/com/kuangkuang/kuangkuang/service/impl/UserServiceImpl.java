package com.kuangkuang.kuangkuang.service.impl;

import com.kuangkuang.kuangkuang.mapper.FriendMapper;
import com.kuangkuang.kuangkuang.mapper.UserMapper;
import com.kuangkuang.kuangkuang.pojo.dto.UserDto;
import com.kuangkuang.kuangkuang.pojo.entity.User;
import com.kuangkuang.kuangkuang.pojo.vo.UserVo;
import com.kuangkuang.kuangkuang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FriendMapper friendMapper;
    @Override
    public UserVo login(UserDto userDto) {
        User user = userMapper.getByUsername(userDto);
        int code = 0;
        UserVo userVo = new UserVo();
        if(user != null){
            code = user.password.equals(userDto.getPassword())?1:0;
            if (code == 1) {
                userVo.setId(user.id);
                userVo.setName(user.getName());
            }
        }

        userVo.setCode(code);

        return userVo;
    }

    @Override
    public UserVo sign(UserDto userDto) {
        Integer code = 0;
        User user = userMapper.getByUsername(userDto);
        if(user ==null){
            code = 1;
            userMapper.add(userDto);
        } else  {
            code=0;
        }
        UserVo userVo = new UserVo();
        userVo.setCode(code);
        return userVo;
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }
    @Override
    public User getUserById(int id) {
        User user = userMapper.getById(id);
        return user;
    }
}
