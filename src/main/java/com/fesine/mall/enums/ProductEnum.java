package com.fesine.mall.enums;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/5/19
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/19
 */
public enum  ProductEnum {

    ON_SALE(1),
    OFF_SALE(2),
    DELETE(3),
        ;
    Integer code;

     ProductEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
