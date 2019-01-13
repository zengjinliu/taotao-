package com.atguigu.dao;

import com.atguigu.bean.User;

public interface UserDao {
    public User findByName(String name);
}
