package com.etoak.bean;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String password;
    private String gender;
    private Integer age;
    private String birthday;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 状态 0：未激活
     * 1：已激活
     */
    private  Integer state;

}
