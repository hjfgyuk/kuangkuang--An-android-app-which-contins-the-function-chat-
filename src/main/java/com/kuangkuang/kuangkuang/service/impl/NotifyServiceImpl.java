package com.kuangkuang.kuangkuang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kuangkuang.kuangkuang.mapper.GroupDetailMapper;
import com.kuangkuang.kuangkuang.mapper.NotifyMapper;
import com.kuangkuang.kuangkuang.pojo.entity.Notify;
import com.kuangkuang.kuangkuang.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.convert.QueryMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotifyServiceImpl implements NotifyService {
    @Autowired
    private NotifyMapper notifyMapper;
    @Autowired
    private GroupDetailMapper groupDetailMapper;
    @Override
    public void add(Notify notify) {
        notifyMapper.insert(notify);
    }

    @Override
    public List<Notify> get(int id) {
        List<Integer> groupId = groupDetailMapper.getByUerId(id);
        QueryWrapper<Notify> query = new QueryWrapper<>();
        query.in("group_id", groupId);
        List<Notify> list = notifyMapper.selectList(query);
        return list;
    }
}
