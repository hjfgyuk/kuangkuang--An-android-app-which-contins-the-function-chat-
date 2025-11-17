package com.kuangkuang.kuangkuang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kuangkuang.kuangkuang.pojo.entity.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper  extends BaseMapper<Message> {
    @Select("SELECT * FROM message WHERE group_id = #{groupId}")
    List<Message> list(int groupId);
    @Insert("INSERT INTO message (user_id,message,time, group_id,user_name,uuid) VALUES (#{userId}, #{message}, now(), #{groupId}, #{userName},#{uuid})")
    void add(Message message);
    @Select("SELECT * FROM message WHERE group_id = #{groupId}")
    List<Message> getByGroupId(int groupId);
}
