package com.fesine.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description: 用户表单
 * @author: fesine
 * @createTime:2021/5/19
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/19
 */
@Data
public class UserForm {

    //@NotNull 用于Integer、对象
    //@NotBlank 用于string
    //@NotEmpty 用于集合
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String email;
}
