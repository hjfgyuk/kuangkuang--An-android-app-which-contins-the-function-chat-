package com.kuangkuang.kuangkuang.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Friend {
    private int id;
    private int user1Id;
    private int user2Id;
    private String friendName;
    private String friendAvatar;
    private LocalDateTime meetAt;
}
