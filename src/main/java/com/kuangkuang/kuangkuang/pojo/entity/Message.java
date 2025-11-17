package com.kuangkuang.kuangkuang.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("message")
public class Message implements Serializable {
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
    @TableField("uuid")
    private String uuid;

}
