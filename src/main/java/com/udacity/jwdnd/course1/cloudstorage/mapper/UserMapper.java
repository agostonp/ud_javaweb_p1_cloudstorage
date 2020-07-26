package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;

import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    
    @Select("SELECT * FROM USERS WHERE userid = #{userid}")
    public User getUserById(Integer userid);
    
    @Select("SELECT * FROM USERS WHERE username = #{username}")
    public User getUserByName(String username);

    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES (#{username}, #{salt}, #{password}, #{firstname}, #{lastname})")
    @Options(useGeneratedKeys=true, keyProperty="userid")
    public int insert(User user);
   
}