package com.kuangkuang.kuangkuang.mapper;

import com.kuangkuang.kuangkuang.pojo.entity.Group;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GroupDetailMapper {

    @Select("SELECT group_id FROM group_detail WHERE user_id = #{userId}")
    List<Integer> getByUerId(int userId);
//TODO 进行重复性检验，避免重复添加好友
    void addFriend(Integer groupId, Object userIds);
}
