package com.kuangkuang.kuangkuang.controller;

import com.kuangkuang.kuangkuang.common.results.Result;
import com.kuangkuang.kuangkuang.pojo.entity.Friend;
import com.kuangkuang.kuangkuang.pojo.entity.User;
import com.kuangkuang.kuangkuang.service.FriendService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private FriendService friendService;
    @PostMapping("/add")
    public Result add(@RequestBody Friend friend){
        log.info("add friend: {}", friend);
        friendService.add(friend);
        return Result.success();
    }
    @GetMapping("/{userId}")
    public Result getFriendsByUserId(@PathVariable("userId") String userId){
        log.info("get friends by userId: {}", userId);
        List<Friend> friends = friendService.getFriendsByUserId(userId);
        return Result.success(friends);
    }
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") String id){
        log.info("delete friend: {}", id);
        friendService.delete(id);
        return Result.success();
    }
}
