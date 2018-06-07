package com.module1.project.mapper;

import com.module1.project.pojo.User;
import com.module1.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface UserMapper extends MyMapper<User> {
    @Select("SELECT username,password,id FROM user" +
            "        where  username = #{username}")
    List<User> byName(String username);
}
