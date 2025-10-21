package com.kuangkuang.kuangkuang.service.impl;

import com.kuangkuang.kuangkuang.mapper.NotifyMapper;
import com.kuangkuang.kuangkuang.pojo.entity.Notify;
import com.kuangkuang.kuangkuang.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotifyServiceImpl implements NotifyService {
    @Autowired
    private NotifyMapper notifyMapper;
    @Override
    public void add(Notify notify) {
        notifyMapper.add(notify);
    }
}
