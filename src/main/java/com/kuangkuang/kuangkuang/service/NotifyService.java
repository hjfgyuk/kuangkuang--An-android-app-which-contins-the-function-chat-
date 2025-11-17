package com.kuangkuang.kuangkuang.service;

import com.kuangkuang.kuangkuang.pojo.entity.Notify;

import java.util.List;

public interface NotifyService {
    void add(Notify notify);

    List<Notify> get(int id);
}

