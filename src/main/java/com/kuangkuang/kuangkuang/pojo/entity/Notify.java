package com.kuangkuang.kuangkuang.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("notify")
public class Notify implements Serializable {
    @TableId
    private int id;
    private int userId;
    private int groupId;
    private String userName;
    private String title;
    //通知内容
    private String content;
    private String time;
    @TableField("deadline")
    private String deadline;
    int type;//默认0 签到为1
}
