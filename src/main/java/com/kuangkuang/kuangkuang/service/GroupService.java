package com.kuangkuang.kuangkuang.service;

import com.kuangkuang.kuangkuang.pojo.entity.Group;
import com.kuangkuang.kuangkuang.pojo.entity.Message;
import com.kuangkuang.kuangkuang.pojo.entity.User;

import java.util.HashMap;
import java.util.List;


public interface GroupService {
    List<Group> get(int id);

    List<Message> getByGroupId(int groupId);

    void delete(int groupId);

    void addFriend(HashMap<String, Object> map);

    void create(Group group, int user);
}
