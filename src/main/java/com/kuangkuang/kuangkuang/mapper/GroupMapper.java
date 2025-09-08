package com.kuangkuang.kuangkuang.mapper;

import com.kuangkuang.kuangkuang.pojo.entity.Group;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GroupMapper {


    List<Group> list(List<Integer> groups);

    @Delete("delete from group where id = #{groupId}")
    void delete(int groupId);
}
