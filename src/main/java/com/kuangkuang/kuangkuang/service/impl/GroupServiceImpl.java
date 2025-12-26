package com.kuangkuang.kuangkuang.service.impl;

import com.kuangkuang.kuangkuang.mapper.GroupDetailMapper;
import com.kuangkuang.kuangkuang.mapper.GroupMapper;
import com.kuangkuang.kuangkuang.mapper.MessageMapper;
import com.kuangkuang.kuangkuang.pojo.entity.Group;
import com.kuangkuang.kuangkuang.pojo.entity.Message;
import com.kuangkuang.kuangkuang.pojo.entity.User;
import com.kuangkuang.kuangkuang.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private GroupDetailMapper groupDetailMapper;
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public List<Group> get(int id) {
        List<Integer> groupIds = groupDetailMapper.getByUerId(id);
        List groups = new ArrayList<>();
        if (groupIds!=null&&!groups.isEmpty()) {//注意要有判断是否为null，否则会返回所有group
            groups = groupMapper.list(groupIds);
        }
        return groups;
    }

    @Override
    public List<Message> getByGroupId(int groupId) {
        List<Message> messages = messageMapper.getByGroupId(groupId);
        return messages;
    }

    @Override
    public void delete(int groupId) {
        //TODO 此次是进入群聊页面后，进入详情页中的解散群聊，而在进入群聊后，客服理应获取用户的身份信息
        groupMapper.delete(groupId);
    }

    /**
     *将好友添加到群聊中
     * @param map
     */
    @Override
    public void addFriend(HashMap<String, Object> map) {
        Integer groupId = Integer.parseInt((String) map.get("groupId"));
        Object userIds =  map.get("list");
        groupDetailMapper.addFriend(groupId, userIds);
    }

    @Transactional
    @Override
    public void create(Group group, int user) {
        groupMapper.add(group, user);
        Group group1 = groupMapper.getByName(group.getName());
        List<Integer> users = new ArrayList<>();
        users.add(user);
        groupDetailMapper.addFriend(group1.getId(), users);
    }
}
