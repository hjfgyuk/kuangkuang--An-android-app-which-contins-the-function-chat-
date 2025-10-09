package com.kuangkuang.kuangkuang.mapper;

import com.kuangkuang.kuangkuang.pojo.entity.Friend;
import com.kuangkuang.kuangkuang.pojo.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FriendMapper {
    @Insert("INSERT INTO friend(user1_id, user2_id,meet_at) VALUES(#{user1Id}, #{user2Id},now())")
    void add(Friend friend);

    @Select("SELECT *, CASE WHEN user1_id = #{userid} THEN user2_id ELSE user1_id END AS user2Id FROM friend WHERE user1_id = #{userid} OR user2_id = #{userid}")
    List<Friend> list(int userId);
    @Select("SELECT * FROM friend where (user1_id = #{user1Id} and user2_id = #{user2Id}) or (user1_id = #{user2Id} and user2_id = #{user1Id})")
    Friend get(Friend friend);
    @Delete("DELETE FROM friend WHERE user2_id = #{id}")
    void delete(int i);
}
