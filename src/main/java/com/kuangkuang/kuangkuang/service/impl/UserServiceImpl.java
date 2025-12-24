package com.kuangkuang.kuangkuang.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kuangkuang.kuangkuang.mapper.FriendMapper;
import com.kuangkuang.kuangkuang.mapper.UserMapper;
import com.kuangkuang.kuangkuang.pojo.dto.UserDto;
import com.kuangkuang.kuangkuang.pojo.entity.User;
import com.kuangkuang.kuangkuang.pojo.vo.UserVo;
import com.kuangkuang.kuangkuang.service.UserService;
import com.kuangkuang.kuangkuang.util.SendMailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FriendMapper friendMapper;
    @Autowired
    private RedisTemplate redisTemplate;
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
            user = userMapper.getByUsername(userDto);
        } else  {
            code=0;
        }
        UserVo userVo = new UserVo();
        userVo.setCode(code);
        if(user!=null)
        userVo.setId(user.getId());
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

    @Override
    public void sendVerificationCode(String email) {
        String code = RandomUtil.randomNumbers(6);
        redisTemplate.opsForValue().set(email,code,Duration.ofMinutes(2));
        SendMailUtil.sendEmailCode(email,code);
    }

    @Override
    public UserVo loginByCode(String phone, String code) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email",phone);
        User user = userMapper.selectOne(queryWrapper);
        UserVo userVo = new UserVo();
        if(user==null){
            userVo.setCode(0);
        }else {
            String trueCode = (String) redisTemplate.opsForValue().get(phone);
            if(trueCode.equals(code)&&trueCode!=null) {
                userVo.setCode(1);
                userVo.setId(user.getId());
                return userVo;
            }else
                userVo.setCode(0);
        }
        return userVo;
    }
}
