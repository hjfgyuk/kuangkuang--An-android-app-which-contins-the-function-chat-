package com.kuangkuang.kuangkuang.controller;

import com.kuangkuang.kuangkuang.common.results.Result;
import com.kuangkuang.kuangkuang.pojo.entity.Message;
import com.kuangkuang.kuangkuang.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @PostMapping("/send")
    public Result send(Message message) {
        log.info("send message");
        messageService.send(message);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result<List<Message>> getMessageById(int id) {
        log.info("get message "+id);
        List<Message> messages = messageService.getMessageById(id);
        return Result.success(messages);
    }
}
