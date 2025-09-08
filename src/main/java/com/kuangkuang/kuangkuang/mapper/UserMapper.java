package com.kuangkuang.kuangkuang.mapper;

import com.kuangkuang.kuangkuang.pojo.dto.UserDto;
import com.kuangkuang.kuangkuang.pojo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE name = #{name}")
    User getByUsername(UserDto userDto);
    @Insert("insert into user(name, password) values(#{name}, #{password})")
    void add(UserDto userDto);

    void update(User user);
}
