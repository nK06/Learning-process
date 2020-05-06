package com.panther.seckill.dao;

import com.panther.seckill.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserDao {
    
    @Select("select *from user where id = #{id}")
    public User getById(@Param("id") long id);
    
    @Insert("insert into user(id,nickname)values(#{id},#(nickname))")
    public int insertUser(User user);
    @Update("update user set password = #{password} where id = #{id}")
    public int  update(User updateUser);
}
