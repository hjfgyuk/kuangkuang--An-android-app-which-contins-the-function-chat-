package com.kuangkuang.kuangkuang.controller;

import com.kuangkuang.kuangkuang.common.results.Result;
import com.kuangkuang.kuangkuang.mapper.GroupDetailMapper;
import com.kuangkuang.kuangkuang.pojo.entity.Message;
import com.kuangkuang.kuangkuang.pojo.entity.User;
import com.kuangkuang.kuangkuang.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.kuangkuang.kuangkuang.pojo.entity.Group;

import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/group")
public class GroupController {
    @Autowired
    private GroupService groupService;
    @PostMapping("/{userId}")
    public Result<List<Group>> get(@PathVariable("userId") int id) {
        log.info("用户"+id+"正在获取群聊列表");
        List<Group> groups = groupService.get(id);
        return Result.success(groups);
    }
    @GetMapping("/{groupId}")
    public Result<List<Message>> getByGroupId(@PathVariable("groupId") String groupId) {
        log.info("获取"+groupId+"群聊消息");
        int id = Integer.parseInt(groupId);
        List<Message> messages = groupService.getByGroupId(id);
        return Result.success(messages);
    }
    @DeleteMapping("/delete/{groupId}")
    public Result delete(@PathVariable("groupId") int groupId){
        log.info("删除群聊"+groupId);
        groupService.delete(groupId);
        return Result.success();
    }
    @PostMapping("/addFriend")
    public Result addFriend(@RequestBody HashMap<String, Object> map){
        log.info("添加好友"+map.get("userIds").toString()+"到群聊"+map.get("groupId"));
        groupService.addFriend(map);
        return Result.success();
    }
    @PostMapping("/create/{userId}")
    public Result create(@RequestBody Group group,@PathVariable("userId") int userId){
        log.info("创建群聊"+group.getName());
        groupService.create(group,userId);
        return Result.success();
    }
}
