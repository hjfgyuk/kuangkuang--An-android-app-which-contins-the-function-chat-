package com.kuangkuang.kuangkuang.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {
    //唯一识别
    private int id;
    //发送者id
    private int userId;
    private String message;
    //发送时间
    private String time;
    //归属的群聊id
    private int groupId;
    private String userName;

}
