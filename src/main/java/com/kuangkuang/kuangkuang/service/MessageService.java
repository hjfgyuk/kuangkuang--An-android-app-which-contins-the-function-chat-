package com.kuangkuang.kuangkuang.service;

import com.kuangkuang.kuangkuang.pojo.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


public interface MessageService {
     void send(Message message);
}
