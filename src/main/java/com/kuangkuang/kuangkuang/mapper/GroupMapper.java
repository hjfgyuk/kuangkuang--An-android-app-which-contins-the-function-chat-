package com.kuangkuang.kuangkuang.mapper;

import com.kuangkuang.kuangkuang.pojo.entity.Group;
import com.kuangkuang.kuangkuang.pojo.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GroupMapper {


    List<Group> list(List<Integer> groups);

    @Delete("delete from `group` where id = #{groupId}")
    void delete(int groupId);
    @Insert("insert into `group` (name, avatar_url, created_at, description,created_by,max_members) values(#{gp.name}, #{gp.avatar},now(),#{gp.description},#{user},#{gp.maxNumber})")
    void add(Group gp, int user);
    @Select("select * from `group` where name = #{groupName}")
    Group getByName(String groupName);
}
