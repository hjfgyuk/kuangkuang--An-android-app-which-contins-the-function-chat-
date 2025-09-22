package com.kuangkuang.kuangkuang.service;

import com.kuangkuang.kuangkuang.pojo.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


public interface MessageService {
     void send(Message message);

    List<Message> getMessageById(int id);
}
