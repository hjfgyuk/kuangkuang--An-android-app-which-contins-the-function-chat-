package com.kuangkuang.kuangkuang.service.impl;

import com.kuangkuang.kuangkuang.mapper.MessageMapper;
import com.kuangkuang.kuangkuang.pojo.entity.Message;
import com.kuangkuang.kuangkuang.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public void send(Message message) {
        messageMapper.add(message);
    }
}
