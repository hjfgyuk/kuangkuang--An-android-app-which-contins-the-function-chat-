package com.kuangkuang.kuangkuang.pojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuangkuang.kuangkuang.config.GetHttpSessionConfig;
import com.kuangkuang.kuangkuang.mapper.MessageMapper;
import com.kuangkuang.kuangkuang.pojo.entity.Message;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@CrossOrigin(origins = "*")
@ServerEndpoint(value = "/chat/{groupId}",configurator = GetHttpSessionConfig.class)
public class ChatPoint {
    @Autowired
    private static MessageMapper messageMapper;
    private static final Map<String, Set<Session>> chatGroups = new ConcurrentHashMap<>();
    //以群聊为单位，存储每个群聊的session，不对，不如直接在请求路径中带上群聊id
    @Autowired
    public void setMessageMapper(MessageMapper messageMapper) {
        ChatPoint.messageMapper = messageMapper;
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
        if(me.getMessage() != null)
        messageMapper.add(me);
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
    private String getGroupIdFromSession(Session session) {
        return (String) session.getUserProperties().get("groupId");
    }
}
