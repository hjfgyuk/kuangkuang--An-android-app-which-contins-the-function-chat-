package com.kuangkuang.kuangkuang.controller;

import com.kuangkuang.kuangkuang.common.results.Result;
import com.kuangkuang.kuangkuang.pojo.entity.Notify;
import com.kuangkuang.kuangkuang.service.NotifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notify")
@Slf4j
public class NotifyController {
    @Autowired
    private NotifyService notifyService;
    @PostMapping("/add")
    public Result add(@RequestBody Notify notify){
        log.info("发布通知"+notify.toString());
        notifyService.add(notify);
        return Result.success();
    }
    @GetMapping("/{userId}")
    public Result<List<Notify>> get(@PathVariable("userId") int id){
        List<Notify> list = notifyService.get(id);
        return Result.success(list);
    }
}
