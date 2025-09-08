package com.kuangkuang.kuangkuang.controller;

import com.kuangkuang.kuangkuang.common.results.Result;
import com.kuangkuang.kuangkuang.pojo.entity.Message;
import com.kuangkuang.kuangkuang.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.kuangkuang.kuangkuang.pojo.entity.Group;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/group")
public class GroupController {
    @Autowired
    private GroupService groupService;
    @PostMapping("/{userId}")
    public Result<List<Group>> get(@PathVariable("userId") int id) {
        List<Group> groups = groupService.get(id);
        log.info("用户"+id+"正在获取群聊列表");
        return Result.success(groups);
    }
    @GetMapping("/{groupId}")
    public Result<List<Message>> getByGroupId(@PathVariable("groupId") int groupId) {
        log.info("获取"+groupId+"群聊消息");
        List<Message> messages = groupService.getByGroupId(groupId);
        return Result.success(messages);
    }
    @DeleteMapping("/delete/{groupId}")
    public Result delete(@PathVariable("groupId") int groupId){
        log.info("删除群聊"+groupId);
        groupService.delete(groupId);
        return Result.success();
    }
}
