package com.kuangkuang.kuangkuang.mapper;

import com.kuangkuang.kuangkuang.pojo.entity.Group;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GroupDetailMapper {

    @Select("SELECT group_id FROM group_detail WHERE user_id = #{userId}")
    List<Integer> getByUerId(int userId);

}
