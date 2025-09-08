package com.kuangkuang.kuangkuang.mapper;

import com.kuangkuang.kuangkuang.pojo.entity.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper  {
    @Select("SELECT * FROM message WHERE groupId = #{groupId} order by created_at desc")
    List<Message> getByGroupId(int groupId);
    @Insert("INSERT INTO message (created_by, text, created_at, groupId) VALUES (#{userId}, #{message}, now(), #{groupId}")
    void add(Message message);
}
