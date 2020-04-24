package com.etoak.service.impl;

import com.etoak.bean.Email;
import com.etoak.bean.User;
import com.etoak.mapper.UserMapper;
import com.etoak.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    JmsTemplate jmsTemplate;

    @Override
    public int addUser(User user) {
        /**
         * 密码加密
         */
        String password = user.getPassword();
        password = DigestUtils.md5Hex(password);
        user.setPassword(password);
        //设置MQActive
        int addResult = userMapper.addUser(user);
        //发送JMS消息
        jmsTemplate.send("email",session -> {
            Email email = new Email();
            email.setSubject("用户激活邮件");
            email.setReceiver(user.getEmail());
            email.setContent("请点击激活：http://location:8080/boot/user/active "+user.getName());
            return session.createTextMessage(JSONObject.toJSONString(email));
        });

        return addResult;
    }
}
