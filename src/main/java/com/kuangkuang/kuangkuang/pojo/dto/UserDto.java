package com.kuangkuang.kuangkuang.pojo.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    private String name;
    private String password;
    @Value("1")
    private String sex;
}
