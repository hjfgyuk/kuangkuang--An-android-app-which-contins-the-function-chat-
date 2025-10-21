package com.kuangkuang.kuangkuang.mapper;

import com.kuangkuang.kuangkuang.pojo.entity.Notify;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotifyMapper {
    @Insert("insert into notify(user_name,user_id, group_id,time,title,content)" +
            "values(#{notify.userName},#{notify.userId},#{notify.groupId},now(),#{notify.title},#{notify.content})")
    void add(Notify notify);
}