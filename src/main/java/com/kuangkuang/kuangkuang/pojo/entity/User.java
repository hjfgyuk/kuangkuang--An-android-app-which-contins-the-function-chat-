package com.kuangkuang.kuangkuang.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    //用户唯一标识
    public int id;
    //用户名
    public String name;
    //密码
    public String password;
    //头像
    public String avatar;
    //默认为1（男）
    @Value("1")
    private String sex;
}
