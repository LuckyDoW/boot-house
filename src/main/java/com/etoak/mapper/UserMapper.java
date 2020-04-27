package com.etoak.mapper;

import com.etoak.bean.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
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
    User queryByName(@Param("name") String name);

}
