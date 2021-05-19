package com.fesine.mall.enums;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/5/19
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/19
 */
public enum RoleEnum {
    ADMIN(0),
    CUSTOMER(1),

    ;
    Integer code;

    public Integer getCode(){
        return code;
    }

    RoleEnum(Integer code) {
        this.code = code;
    }
}
