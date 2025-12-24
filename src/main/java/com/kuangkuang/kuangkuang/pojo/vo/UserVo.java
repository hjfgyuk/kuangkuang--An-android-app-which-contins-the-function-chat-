package com.kuangkuang.kuangkuang.pojo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserVo implements Serializable {
    private int id;
    private String name;
    private String password;
    private String avatar;
    private String sex;
    private int code;
    private String token;
    private String email;
}
