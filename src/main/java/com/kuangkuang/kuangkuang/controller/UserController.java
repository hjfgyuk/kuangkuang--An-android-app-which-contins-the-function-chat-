package com.kuangkuang.kuangkuang.controller;

import com.kuangkuang.kuangkuang.common.results.Result;
import com.kuangkuang.kuangkuang.pojo.dto.UserDto;
import com.kuangkuang.kuangkuang.pojo.entity.User;
import com.kuangkuang.kuangkuang.pojo.vo.UserVo;
import com.kuangkuang.kuangkuang.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

        log.info("login user: "+userVo.toString());
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
