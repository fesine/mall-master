package com.fesine.mall.enums;

/**
 * @description: 响应码
 * @author: fesine
 * @createTime:2021/5/19
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/19
 */
public enum ResponseEnum {
    ERROR(-1,"服务端错误"),
    SUCCESS(0,"成功"),
    PASSWORD_ERROR(1,"密码错误"),
    USERNAME_EXIST(2, "用户已存在"),
    EMAIL_EXIST(4, "邮箱已存在"),
    PARAM_ERROR(3, "参数有误"),
    NEED_LOGIN(10,"用户未登录，请先登录"),
    ;
    Integer code;
    String desc;
    ResponseEnum(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
