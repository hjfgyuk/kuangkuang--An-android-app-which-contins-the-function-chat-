package com.kuangkuang.kuangkuang.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Group implements Serializable {
    //群聊唯一识别方式
    private int id;
    private String name;
    private String avatar;
//    //未读的信息数
//    private int messageUnread;
    //创立时间
    private LocalDateTime createTime;
//    //群成员列表
//    private List<Integer> members;

}