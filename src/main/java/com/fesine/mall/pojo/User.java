package com.fesine.mall.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @description: 用户实体
 * @author: fesine
 * @createTime:2021/5/19
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/19
 */
@Data
public class User {
    private Integer id;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String question;

    private String answer;

    private Integer role;

    private Date createTime;

    private Date updateTime;

    public User(String username, String password, String email,Integer role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}