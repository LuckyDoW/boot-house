package com.etoak.service;

import com.etoak.bean.User;

public interface UserService {
    /**
     * 注册添加用户
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 查询用户名是否存在
     * @param name
     * @return
     */
    User queryByName(String name);
}
