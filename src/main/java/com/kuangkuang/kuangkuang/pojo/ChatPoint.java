package com.kuangkuang.kuangkuang.pojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuangkuang.kuangkuang.config.GetHttpSessionConfig;
import com.kuangkuang.kuangkuang.mapper.MessageMapper;
import com.kuangkuang.kuangkuang.pojo.entity.AuditResult;
import com.kuangkuang.kuangkuang.pojo.entity.Message;
import com.kuangkuang.kuangkuang.service.AIContentAuditService;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
@CrossOrigin(origins = "*")
@ServerEndpoint(value = "/chat/{groupId}",configurator = GetHttpSessionConfig.class)
public class ChatPoint {

    private static RabbitTemplate rabbitTemplate;
    @Autowired
    private static MessageMapper messageMapper;
    @Autowired
    private static AIContentAuditService aiContentAuditService;
    private static final Map<String, Set<Session>> chatGroups = new ConcurrentHashMap<>();
    //以群聊为单位，存储每个群聊的session，不对，不如直接在请求路径中带上群聊id
    @Autowired
    public void setMessageMapper(MessageMapper messageMapper) {
        ChatPoint.messageMapper = messageMapper;
    }
    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        log.info("RabbitTemplate 已注入到 ChatPoint");
    }

    @Autowired
    public void setAiAuditService(AIContentAuditService auditService) {
        ChatPoint.aiContentAuditService = auditService;
    }
    @OnOpen
    public void onOpen(Session session,@PathParam("groupId") String groupId) {
        session.getUserProperties().put("groupId", groupId);
        chatGroups.computeIfAbsent(groupId, k -> new HashSet<>()).add(session);
        System.out.println("新的连接："+session.getId()+"加入群聊"+groupId);
    }
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Received message: " + message);
        //mapper更新数据库
        ObjectMapper objectMapper = new ObjectMapper();
        Message me = new Message();
        try {
            me = objectMapper.readValue(message, Message.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String uuid = UUID.randomUUID().toString();
        me.setUuid(uuid);
        sendMessageToAi(me);
        Message finalMe = me;
            try{
                    messageMapper.add(finalMe);
                    String groupId = getGroupIdFromSession(session);
                    if (groupId != null) {
                        // 广播消息到同一群聊
                        Set<Session> sessions = chatGroups.get(groupId);
                        for (Session s : sessions) {
                            if (s.isOpen() && !s.equals(session)) {
                                try {
                                    s.getBasicRemote().sendText(message);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
            }
            catch(Exception e){
                log.error("消息处理错误:"+e.getMessage());
            }
    }
    @OnClose
    public void onClose(Session session) {
        String groupId = getGroupIdFromSession(session);
        if (groupId != null) {
            chatGroups.get(groupId).remove(session);
            if (chatGroups.get(groupId).isEmpty()) {
                chatGroups.remove(groupId); // 清理空的群聊
            }
            System.out.println("Connection closed in group " + groupId + ": " + session.getId());
        }
    }
    //发送消息到mq中
    public void sendMessageToAi(Message message){
            log.info("发送消息"+message+"到mq中");
            try{
            rabbitTemplate.convertAndSend(
                    "ai.direct",
                    "check",
                    message);}
            catch (Exception e){
                log.info("发送消息到mq失败"+e.getMessage());
                throw e;
            }
    }
    private String getGroupIdFromSession(Session session) {
        return (String) session.getUserProperties().get("groupId");
    }
}
