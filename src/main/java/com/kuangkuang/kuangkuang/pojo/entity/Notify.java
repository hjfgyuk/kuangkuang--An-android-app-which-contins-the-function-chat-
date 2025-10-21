package com.kuangkuang.kuangkuang.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notify {
    private int id;
    private int userId;
    private int groupId;
    private String userName;
    private String title;
    //通知内容
    private String content;
    private String time;
}
