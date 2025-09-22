package com.kuangkuang.kuangkuang.service;

import com.kuangkuang.kuangkuang.pojo.entity.Friend;
import com.kuangkuang.kuangkuang.pojo.entity.User;

import java.util.List;

public interface FriendService {
    void add(Friend friend);

    List<Friend> getFriendsByUserId(String userId);

    void delete(String id);
}
