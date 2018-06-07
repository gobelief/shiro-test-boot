package com.module1.project.service;

import com.github.pagehelper.PageHelper;
import com.module1.project.mapper.UserMapper;
import com.module1.project.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public List<User> byName(String username){
        return userMapper.byName(username);
    }
    public List<User> getAll(User user) {
        if (user.getPage() != null && user.getRows() != null) {
            PageHelper.startPage(user.getPage(), user.getRows());
        }
        return userMapper.selectAll();
    }
}
