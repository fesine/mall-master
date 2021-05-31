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
    USERNAME_OR_PASSWORD_ERROR(11,"用户名或密码错误"),
    PRODUCT_OFF_SALE_OR_DELETE(12,"该商品已下架或删除"),
    PRODUCT_NOT_EXIST(13,"商品不存在"),
    PRODUCT_STOCK_ERROR(14,"商品库存不足"),
    CART_PRODUCT_NOT_EXIST(15,"购物车里无此商品"),
    DELETE_SHIPPING_FAILED(16,"收货地址删除失败"),
    UPDATE_SHIPPING_FAILED(17,"收货地址更新失败"),
    SHIPPING_NOT_EXIST(18,"收货地址不存在"),
    CART_PRODUCT_SELECTED_IS_EMPTY(19,"请选择商品后下单"),
    ORDER_NOT_EXIST(20,"订单不存在"),
    ORDER_STATUS_ERROR(21,"订单状态有误"),
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
