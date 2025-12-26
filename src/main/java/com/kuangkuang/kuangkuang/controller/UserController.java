package com.kuangkuang.kuangkuang.controller;

import cn.hutool.core.lang.Validator;
import com.kuangkuang.kuangkuang.common.results.Result;
import com.kuangkuang.kuangkuang.pojo.dto.UserDto;
import com.kuangkuang.kuangkuang.pojo.entity.User;
import com.kuangkuang.kuangkuang.pojo.vo.UserVo;
import com.kuangkuang.kuangkuang.service.UserService;
import com.kuangkuang.kuangkuang.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RegExUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/get")
    public Result<User> get(@RequestBody User user){
        log.info("get user: "+user.toString());
        User user1 = userService.getUserById(user.getId());
        return Result.success(user1);
    }

    @PostMapping("/login")
    public Result<UserVo> login(@RequestBody UserDto userDto){
        log.info("login user: " + userDto.toString());
        UserVo userVo = userService.login(userDto);
        Map<String,Object> map = new HashMap<>();
        map.put(JwtUtil.empId,userVo.getId());
        String token = JwtUtil.createJwt(map);
        userVo.setToken(token);
        log.info("login user: "+userVo.toString());
        return Result.success(userVo);
    }
    @PostMapping("/login/{email}")
    public Result getEmailCode(@PathVariable("email") String email){
        log.info(email+"请求短信验证码");
        if(!Validator.isEmail(email))
            return Result.error("邮箱格式错误");
        userService.sendVerificationCode(email);
        return Result.success();
    }
    @PostMapping("/loginByCode")
    public Result<UserVo> loginByCode(@RequestBody User user){
        log.info(user+"请求登录");
        String email = user.getEmail();
        String code = user.getVerificationCode();
        UserVo userVo = userService.loginByCode(email,code);
        Map<String,Object> map = new HashMap<>();
        map.put(JwtUtil.empId,userVo.getId());
        String token = JwtUtil.createJwt(map);
        userVo.setToken(token);
        return Result.success(userVo);
    }

    @PostMapping("/sign")
    public Result sign(@RequestBody UserDto userDto){
        log.info("sign user: " + userDto.toString());
        UserVo userVo = userService.sign(userDto);
        if(userVo.getCode()==0){
            return Result.error("用户名已存在");
        }
        return Result.success(userVo);
    }
    @PostMapping("/update")
    public Result update(@RequestBody User user){
        log.info("修改员工信息"+user.toString());
        userService.update(user);
        return Result.success();
    }
    @PostMapping("/logout")
    public Result logout(){
        return Result.success();
    }

}
