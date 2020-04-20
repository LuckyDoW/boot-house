package com.etoak.bean;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String naem;
    private String password;
    private String gender;
    private Integer age;
    private String birthday;

}
