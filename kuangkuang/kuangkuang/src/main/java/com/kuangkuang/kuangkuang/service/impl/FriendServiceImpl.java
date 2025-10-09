package com.kuangkuang.kuangkuang.service.impl;

import com.kuangkuang.kuangkuang.mapper.FriendMapper;
import com.kuangkuang.kuangkuang.mapper.UserMapper;
import com.kuangkuang.kuangkuang.pojo.entity.Friend;
import com.kuangkuang.kuangkuang.pojo.entity.User;
import com.kuangkuang.kuangkuang.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendMapper friendMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void add(Friend friend) {
        Friend f = friendMapper.get(friend);
        if (f== null) {
            friendMapper.add(friend);
        }
    }

    @Override
    public List<Friend> getFriendsByUserId(String userId) {
        List<Friend> friends = friendMapper.list(Integer.parseInt(userId));
        for (Friend friend : friends) {
            friend.setFriendName(userMapper.getById(friend.getUser2Id()).getName());
        }
        return friends;
    }

    @Override
    public void delete(String id) {
        friendMapper.delete(Integer.parseInt(id));
    }
}
